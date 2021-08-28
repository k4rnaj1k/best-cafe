package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Table(name = "ingredients")
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<Dish> dishes;

    public static Ingredient fromDTO(IngredientDTO ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }
}
