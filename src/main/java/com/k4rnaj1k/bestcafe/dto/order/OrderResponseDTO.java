package com.k4rnaj1k.bestcafe.dto.order;

import com.k4rnaj1k.bestcafe.model.order.Order;

import java.util.List;

public record OrderResponseDTO(Long id, List<DishOrderDTO> dishOrders, List<DrinkOrderDTO> drinkOrders, Double price,
                               Order.OrderStatus status) {
    public static OrderResponseDTO fromOrder(Order save, List<DishOrderDTO> dishOrders, List<DrinkOrderDTO> drinks) {
        return new OrderResponseDTO(save.getId(),
                dishOrders,
                drinks,
                save.getPrice(),
                save.getStatus());
    }
}
