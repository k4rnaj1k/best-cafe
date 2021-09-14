package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.model.order.Order;
import com.k4rnaj1k.bestcafe.service.OrderService;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(Routes.ORDERS)
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public Order createOrder(@RequestBody @Valid OrderDTO order, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.createOrder(order, user);
    }

    @GetMapping
    public List<Order> getOrders(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getOrders(user);
    }

    @PutMapping("/statuses/{id}")
    public Order updateOrderStatus(@PathVariable("id") Long orderId, Order.OrderStatus orderStatus) {
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @PutMapping("{id}")
    public Order updateOrder(@RequestBody @Valid OrderDTO orderDTO, @PathVariable("id") Long orderId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.updateOrder(orderDTO, orderId, user);
    }
}
