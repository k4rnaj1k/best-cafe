package com.k4rnaj1k.bestcafe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drink_order_items")
@Data
public class DrinkItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @OneToOne
    private Drink drink;

    private Long amount;
}
