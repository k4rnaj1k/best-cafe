package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drink_orders")
@Data
public class DrinkItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private Long id;

    @ManyToOne
    @JsonView(Views.PostOrder.class)
    private Drink drink;

    @JsonView(Views.PostOrder.class)
    private Long amount;
}
