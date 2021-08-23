package com.k4rnaj1k.bestcafe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dish_order_items")
@Data
public class DishItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Dish dish;

    private Long amount;
}
