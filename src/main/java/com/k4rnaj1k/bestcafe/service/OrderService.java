package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.dto.order.responce.DishOrderResponseDTO;
import com.k4rnaj1k.bestcafe.dto.order.responce.DrinkOrderResponceDTO;
import com.k4rnaj1k.bestcafe.dto.order.responce.OrderResponseDTO;
import com.k4rnaj1k.bestcafe.exception.CafeExceptionUtils;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.model.order.DishOrder;
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
            throw CafeExceptionUtils.emptyOrderException();
        }
        loadOrderFields(order);

        order.setDishes(dishOrderRepository.saveAll(order.getDishes()));
        order.setDrinks(drinkOrderRepository.saveAll(order.getDrinks()));

        order = orderRepository.save(order);

        return OrderResponseDTO.fromOrder(order, mapDishesToResponceDTO(order.getDishes()), mapDrinksToResponceDTO(order.getDrinks()));
    }

    private void loadOrderFields(Order order) {
        loadDishes(order);
        loadDrinks(order);

    }

    private void loadDrinks(Order order) {
        order.getDrinks().forEach(drinkOrder -> {
            Long drinkId = drinkOrder.getDrink().getId();
            drinkOrder.setDrink(drinkRepository.findById(drinkId).orElseThrow(() -> CafeExceptionUtils.drinkDoesntExist(drinkId)));
        });
    }

    private void loadDishes(Order order) {
        order.getDishes().forEach(dishOrder -> {
            Long dishId = dishOrder.getDish().getId();
            dishOrder.setDish(dishRepository.findById(dishId).orElseThrow(() -> CafeExceptionUtils.dishDoesntExist(dishId)));
        });

        order.getDishes().forEach(dishOrder -> {
            if (dishOrder.getDish().getIngredients().size() <= dishOrder.getExcluded().size())
                throw CafeExceptionUtils.tooManyExcludedIngredientsException();
            if (!dishOrder.getDish().getIngredients().containsAll(dishOrder.getExcluded())) {
                throw CafeExceptionUtils.excludedIngredientsException();
            }
        });
        order.getDishes().forEach(dishOrder -> dishOrder.getExcluded().forEach(ingredient -> {
                    Long id = ingredient.getId();
                    ingredient.setName(ingredientRepository.findById(id).orElseThrow(() -> CafeExceptionUtils.ingredientDoesntExist(id)).getName());
                }
        ));
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrders(User user) {
        List<Role> userRoles = user.getRoles();
        List<Order> orders;
        if (userRoles.contains(roleRepository.findByName("ROLE_ADMIN")) || userRoles.contains(roleRepository.findByName("ROLE_COOK")))
            orders = orderRepository.findAll();
        else
            orders = orderRepository.findAllByUser(user);
        return orders.stream().map(this::mapToResponseDTO).toList();
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        List<DishOrderResponseDTO> dishResponse = mapDishesToResponceDTO(order.getDishes());
        List<DrinkOrderResponceDTO> drinkResponse = mapDrinksToResponceDTO(order.getDrinks());
        return OrderResponseDTO.fromOrder(order, dishResponse, drinkResponse);
    }

    private List<DrinkOrderResponceDTO> mapDrinksToResponceDTO(List<DrinkOrder> drinks) {
        List<DrinkOrderResponceDTO> result = new ArrayList<>();
        drinks.forEach(drinkOrder ->
                result.add(new DrinkOrderResponceDTO(drinkOrder.getDrink().getId(), drinkOrder.getDrink().getName(), drinkOrder.getAmount(), drinkOrder.getDrink().getPrice()))
        );
        return result;
    }

    private List<DishOrderResponseDTO> mapDishesToResponceDTO(List<DishOrder> dishOrders) {
        List<DishOrderResponseDTO> result = new ArrayList<>();
        dishOrders.forEach(dishOrder ->
                result.add(new DishOrderResponseDTO(dishOrder.getDish().getId(), dishOrder.getDish().getName(), dishOrder.getAmount(), dishOrder.getDish().getPrice()))
        );
        return result;
    }

    public OrderResponseDTO updateOrderStatus(Long orderId, Order.OrderStatus updatedOrderStatus) {
        Order orderFromDb = getOrderById(orderId);
        orderFromDb.setUpdatedAt(Instant.now());
        if (orderFromDb.getStatus().getValue() >= updatedOrderStatus.getValue()) {
            throw CafeExceptionUtils.orderStatusException();
        }
        orderFromDb.setStatus(updatedOrderStatus);
        return mapToResponseDTO(orderRepository.save(orderFromDb));
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> CafeExceptionUtils.orderDoesntExist(orderId));
    }

    public Order updateOrder(OrderDTO orderDTO, Long orderId, User user) {
        Order order = getOrderById(orderId);
        if (order.getStatus().getValue() > 1) {
            throw CafeExceptionUtils.orderAcceptedException(orderId);
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


