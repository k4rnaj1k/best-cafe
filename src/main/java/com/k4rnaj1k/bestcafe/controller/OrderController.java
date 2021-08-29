package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.Order;
import com.k4rnaj1k.bestcafe.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.ORDERS)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDTO order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PutMapping("/{id}")
    public Order updateOrderStatus(@PathVariable("id") Long orderId, Order.OrderStatus orderStatus) {
        return orderService.updateOrderStatus(orderId, orderStatus);
    }
}
