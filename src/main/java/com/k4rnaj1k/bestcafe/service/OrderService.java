package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.dto.order.OrderResponseDTO;
import com.k4rnaj1k.bestcafe.exception.CafeException;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import com.k4rnaj1k.bestcafe.model.order.DrinkOrder;
import com.k4rnaj1k.bestcafe.model.order.Order;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.repository.menu.DishRepository;
import com.k4rnaj1k.bestcafe.repository.menu.DrinkRepository;
import com.k4rnaj1k.bestcafe.repository.menu.IngredientRepository;
import com.k4rnaj1k.bestcafe.repository.order.DishOrderRepository;
import com.k4rnaj1k.bestcafe.repository.order.DrinkOrderRepository;
import com.k4rnaj1k.bestcafe.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DrinkOrderRepository drinkOrderRepository;
    private final DishOrderRepository dishOrderRepository;
    private final RoleRepository roleRepository;
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;


    public OrderResponseDTO createOrder(OrderDTO orderDTO, User user) {
        Order order = Order.fromDTO(orderDTO);
        order.setUser(user);

        if (order.getDishes().size() == 0 && order.getDrinks().size() == 0) {
            throw CafeException.emptyOrderException();
        }
        loadOrderFields(order);

        //order.setDrinks(drinkOrderRepository.saveAll());
        order.setDishes(dishOrderRepository.saveAll(order.getDishes()));
        order.setDrinks(drinkOrderRepository.saveAll(order.getDrinks()));
   //     return orderRepository.save(order);
        return OrderResponseDTO.fromOrder(orderRepository.save(order), orderDTO.dishes(), orderDTO.drinks());
    }

    private void loadOrderFields(Order order) {
        loadDishes(order);
        loadDrinks(order);

    }

    private void loadDrinks(Order order) {
        order.getDrinks().forEach(drinkOrder -> {
            Long drinkId = drinkOrder.getDrink().getId();
            drinkOrder.setDrink(drinkRepository.findById(drinkId).orElseThrow(()->CafeException.drinkDoesntExist(drinkId)));
        });
    }

    private void loadDishes(Order order) {
        order.getDishes().forEach(dishOrder -> {
            Long dishId = dishOrder.getDish().getId();
            dishOrder.setDish(dishRepository.findById(dishId).orElseThrow(() -> CafeException.dishDoesntExist(dishId)));
        });

        order.getDishes().forEach(dishOrder -> {
            if (dishOrder.getDish().getIngredients().size() <= dishOrder.getExcluded().size())
                throw CafeException.tooManyExcludedIngredientsException();
            if (!dishOrder.getDish().getIngredients().containsAll(dishOrder.getExcluded())) {
                throw CafeException.excludedIngredientsException();
            }
        });
        order.getDishes().forEach(dishOrder -> dishOrder.getExcluded().forEach(ingredient -> {
                    Long id = ingredient.getId();
                    ingredient.setName(ingredientRepository.findById(id).orElseThrow(() -> CafeException.ingredientDoesntExist(id)).getName());
                }
        ));
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
        return orderRepository.save(orderFromDb);
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> CafeException.orderDoesntExist(orderId));
    }

    public Order updateOrder(OrderDTO orderDTO, Long orderId, User user) {
        Order order = getOrderById(orderId);
        if (order.getStatus().getValue() > 1) {
            throw CafeException.orderAcceptedException(orderId);
        }

        checkOrderAccess(orderId, user, order);
        Order update = Order.fromDTO(orderDTO);
        loadOrderFields(update);
        update.setCreatedAt(order.getCreatedAt());
        update.setUpdatedAt(Instant.now());
        update.setDishes(dishOrderRepository.saveAll(update.getDishes()));
        update.setDrinks(drinkOrderRepository.saveAll(update.getDrinks()));

        BeanUtils.copyProperties(update, order, "id", "user", "createdAt");
        return orderRepository.save(order);
    }

    private void checkOrderAccess(Long orderId, User user, Order order) {
        if (!(order.getUser().equals(user) || user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN")))) {
            throw new AuthorizationServiceException("Access to order " + orderId + " denied.");
        }
    }
}


