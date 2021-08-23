package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dishes")
@Data
public class Dish {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonIgnoreProperties("dishes")
    private List<Ingredient> ingredients;
}
