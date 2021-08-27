package com.k4rnaj1k.bestcafe.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@JsonView(Views.Get.class)
@Slf4j
public class ItemController {
    private final MenuService itemService;

    public ItemController(MenuService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("ingredients")
    public Ingredient createIngredient(@RequestBody @JsonView(Views.PostIngredient.class) Ingredient ingredient) {
        log.info("{}", ingredient.getName());
        return itemService.createIngredient(ingredient);
    }

    @GetMapping("drinks")
    public List<Drink> getDrinks() {
        return itemService.getDrinks();
    }

    @GetMapping("dishes")
    public List<Dish> getDishes() {
        return itemService.getDishes();
    }

    @PostMapping("dishes")
    public Dish createDish(@RequestBody @JsonView(Views.PostDish.class) Dish dish) {
        return itemService.createDish(dish);
    }

    @GetMapping("ingredients")
    public List<Ingredient> getIngredients() {
        return itemService.getIngredients();
    }

    @PutMapping("ingredients/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long ingredientId, @RequestBody @JsonView(Views.PostIngredient.class) Ingredient updatedIngredient) {
        return itemService.updateIngredient(ingredientId, updatedIngredient);
    }
}
