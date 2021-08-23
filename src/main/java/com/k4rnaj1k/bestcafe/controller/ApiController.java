package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.model.Order;
import com.k4rnaj1k.bestcafe.service.ItemsService;
import com.k4rnaj1k.bestcafe.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    private final OrderService orderService;
    private final ItemsService itemService;

    public ApiController(OrderService orderService, ItemsService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    @PostMapping("/ingredients")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient){
        return ingredient;
    }

    @GetMapping("drinks")
    public List<Drink> getDrinks() {
        return itemService.getDrinks();
    }

    @GetMapping("dishes")
    public List<Dish> getDishes() {
        return itemService.getDishes();
    }

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
