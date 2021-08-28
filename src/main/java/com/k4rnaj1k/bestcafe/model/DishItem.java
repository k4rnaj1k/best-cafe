package com.k4rnaj1k.bestcafe.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish_orders")
@Data
public class DishItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dish dish;

    private Long amount;

    @ManyToMany
    @JoinTable(name = "excluded_dish_ingredients", joinColumns = @JoinColumn(name = "dish_order_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> excluded = new ArrayList<>();
}
