package com.k4rnaj1k.bestcafe.dto.order.responce;

import com.k4rnaj1k.bestcafe.model.order.Order;

import java.util.List;

public record OrderResponseDTO(Long id, List<DishOrderResponseDTO> dishOrders, List<DrinkOrderResponceDTO> drinkOrders, Double price,
                               Order.OrderStatus status) {
    public static OrderResponseDTO fromOrder(Order save, List<DishOrderResponseDTO> dishOrders, List<DrinkOrderResponceDTO> drinkOrders) {
        return new OrderResponseDTO(save.getId(),
                dishOrders,
                drinkOrders,
                save.getPrice(),
                save.getStatus());
    }
}
