package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
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
    @JsonView({Views.Get.class})
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(Views.PostOrder.class)
    private Dish dish;

    @JsonView(Views.PostOrder.class)
    private Long amount;

    @ManyToMany
    @JoinTable(name = "excluded_dish_ingredients", joinColumns = @JoinColumn(name = "dish_order_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonView(Views.PostOrder.class)
    private List<Ingredient> excluded = new ArrayList<>();
}
