package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dishes")
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Get.class, Views.PostOrder.class})
    private Long id;

    @Column(unique = true)
    @JsonView({Views.PostDish.class})
    @NotBlank
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonView(Views.PostDish.class)
    private List<Ingredient> ingredients = new ArrayList<>();
}
