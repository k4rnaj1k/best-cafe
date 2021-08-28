package com.k4rnaj1k.bestcafe.model;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
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
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    private Double price;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    public static Dish fromPostDTO(DishPostDTO dishPostDTO) {
        Dish dish = new Dish();
        dish.setName(dishPostDTO.getName());
        List<Ingredient> ingredients = new ArrayList<>();
        dishPostDTO.getIngredients().forEach(id -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(id);
            ingredients.add(ingredient);
        });
        dish.setIngredients(ingredients);
        dish.setPrice(dishPostDTO.getPrice());
        return dish;
    }
}
