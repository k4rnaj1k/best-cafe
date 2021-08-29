package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.exception.CafeException;
import com.k4rnaj1k.bestcafe.model.order.DishOrder;
import com.k4rnaj1k.bestcafe.model.order.DrinkOrder;
import com.k4rnaj1k.bestcafe.model.order.Order;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.repository.menu.DishRepository;
import com.k4rnaj1k.bestcafe.repository.menu.IngredientRepository;
import com.k4rnaj1k.bestcafe.repository.order.DishItemRepository;
import com.k4rnaj1k.bestcafe.repository.order.DrinkItemRepository;
import com.k4rnaj1k.bestcafe.repository.order.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DrinkItemRepository drinkItemRepository;
    private final DishItemRepository dishItemRepository;
    private final RoleRepository roleRepository;
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;

    public OrderService(OrderRepository orderRepository, DrinkItemRepository drinkItemRepository, DishItemRepository dishItemRepository, RoleRepository roleRepository, IngredientRepository ingredientRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.drinkItemRepository = drinkItemRepository;
        this.dishItemRepository = dishItemRepository;
        this.roleRepository = roleRepository;
        this.ingredientRepository = ingredientRepository;
        this.dishRepository = dishRepository;
    }

    public Order createOrder(OrderDTO orderDTO, User user) {
        Order order = Order.fromDTO(orderDTO);
        order.setUser(user);

        if (order.getDishes().size() == 0 && order.getDrinks().size() == 0) {
            throw CafeException.emptyOrderException();
        }

        loadOrderFields(order);

        order.setDishes(dishItemRepository.saveAll(order.getDishes()));
        order.setDrinks(drinkItemRepository.saveAll(order.getDrinks()));
        order.setPrice(countPrice(order));
        return orderRepository.save(order);
    }

    private void loadOrderFields(Order order) {
        order.getDishes().forEach(dishOrder -> {
            Long dishId = dishOrder.getDish().getId();
            dishOrder.setDish(dishRepository.findById(dishId).orElseThrow(() -> CafeException.dishDoesntExist(dishId)));
        });

        order.getDishes().forEach(dishOrder -> {
            if (dishOrder.getDish().getIngredients().size() <= dishOrder.getExcluded().size())
                throw CafeException.tooManyExcludedIngredientsException();
        });
        order.getDishes().forEach(dishOrder -> dishOrder.getExcluded().forEach(ingredient -> {
                    Long id = ingredient.getId();
                    ingredient.setName(ingredientRepository.findById(id).orElseThrow(() -> CafeException.ingredientDoesntExist(id)).getName());
                }
        ));
    }

    private Double countPrice(Order order) {
        double price = 0;
        for (DishOrder dish :
                order.getDishes()) {
            price += dish.getDish().getPrice();
        }

        for (DrinkOrder drink :
                order.getDrinks()) {
            price += drink.getDrink().getPrice();
        }
        return price;
    }

    public List<Order> getOrders(User user) {
        List<Role> userRoles = user.getRoles();
        if (userRoles.contains(roleRepository.findByName("ROLE_ADMIN")) || userRoles.contains(roleRepository.findByName("ROLE_COOK")))
            return orderRepository.findAll();
        else
            return orderRepository.findAllByUser(user);
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus updatedOrderStatus) {
        Order orderFromDb = getOrderById(orderId);
        orderFromDb.setUpdatedAt(Instant.now());
        if (orderFromDb.getStatus().getValue() >= updatedOrderStatus.getValue()) {
            throw CafeException.orderStatusException();
        }
        orderFromDb.setStatus(updatedOrderStatus);
        return orderFromDb;
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> CafeException.orderDoesntExist(orderId));
    }

    public Order updateOrder(OrderDTO orderDTO, Long orderId, User user) {
        Order order = getOrderById(orderId);
        if (order.getUser() != user && !user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            throw new AuthorizationServiceException("Access to order " + orderId + " denied.");
        }
        if (order.getStatus().getValue() > 1) {
            throw CafeException.orderAcceptedException(orderId);
        }

        Order update = Order.fromDTO(orderDTO);
        update.setId(order.getId());
        loadOrderFields(update);
        update.setPrice(countPrice(update));
        update.setUser(user);
        update.setCreatedAt(order.getCreatedAt());
        update.setUpdatedAt(Instant.now());
        update.setDishes(dishItemRepository.saveAll(update.getDishes()));
        update.setDrinks(drinkItemRepository.saveAll(update.getDrinks()));

        BeanUtils.copyProperties(update, order);
        return orderRepository.save(order);
    }
}
