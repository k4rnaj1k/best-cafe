package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.exception.CafeException;
import com.k4rnaj1k.bestcafe.model.Order;
import com.k4rnaj1k.bestcafe.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DrinkItemRepository drinkItemRepository;
    private final DishItemRepository dishItemRepository;
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;

    public OrderService(OrderRepository orderRepository, DrinkItemRepository drinkItemRepository, DishItemRepository dishItemRepository, IngredientRepository ingredientRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.drinkItemRepository = drinkItemRepository;
        this.dishItemRepository = dishItemRepository;
        this.ingredientRepository = ingredientRepository;
        this.dishRepository = dishRepository;
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = Order.fromDTO(orderDTO);
        if (order.getDishes().size() == 0 && order.getDrinks().size() == 0) {
            throw CafeException.emptyOrderException();
        }
        order.getDishes().forEach(dishItem -> {
            Long id = dishItem.getDish().getId();
            dishItem.setDish(dishRepository.findById(id).orElseThrow(() -> CafeException.dishDoesntExist(id)));
        });
        order.getDishes().forEach(dishItem -> {
            if (dishItem.getDish().getIngredients().size() <= dishItem.getExcluded().size())
                throw CafeException.tooManyExcludedIngredientsException();
        });
        order.getDishes().forEach(dishItem -> dishItem.getExcluded().forEach(ingredient -> {
                    Long id = ingredient.getId();
                    ingredient.setName(ingredientRepository.findById(id).orElseThrow(() -> CafeException.ingredientDoesntExist(id)).getName());
                }
        ));
        order.setDishes(dishItemRepository.saveAll(order.getDishes()));
        order.setDrinks(drinkItemRepository.saveAll(order.getDrinks()));
        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus updatedOrderStatus) {
        Order orderFromDb = orderRepository.findById(orderId).orElseThrow(() -> CafeException.orderDoesntExist(orderId));
        if (orderFromDb.getStatus().getValue() >= updatedOrderStatus.getValue()) {
            throw CafeException.orderStatusException();
        }
        orderFromDb.setStatus(updatedOrderStatus);
        return orderFromDb;
    }
}
