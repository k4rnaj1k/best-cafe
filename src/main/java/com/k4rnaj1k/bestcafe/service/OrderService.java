package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.model.Order;
import com.k4rnaj1k.bestcafe.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
