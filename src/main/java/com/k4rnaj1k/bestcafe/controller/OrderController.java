package com.k4rnaj1k.bestcafe.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.model.Order;
import com.k4rnaj1k.bestcafe.service.MenuService;
import com.k4rnaj1k.bestcafe.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders")
    public Order createOrder(@RequestBody @JsonView(Views.PostOrder.class) Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PutMapping("orders/statuses/{id}")
    public Order updateOrderStatus(@PathVariable("id") Long orderId, Order.OrderStatus orderStatus){
        return orderService.updateOrderStatus(orderId, orderStatus);
    }
}
