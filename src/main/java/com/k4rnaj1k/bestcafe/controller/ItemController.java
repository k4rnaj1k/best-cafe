package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.API_URL)
@Slf4j
public class ItemController {
    private final MenuService itemService;

    public ItemController(MenuService itemService) {
        this.itemService = itemService;
    }

    @DeleteMapping("ingredients/{id}")
    public void removeIngredient(@PathVariable("id") Long ingredientId) {
        itemService.removeIngredientById(ingredientId);
    }

    @PostMapping("ingredients")
    public Ingredient createIngredient(@RequestBody IngredientDTO ingredientDto) {
        return itemService.createIngredient(ingredientDto);
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
    public Dish createDish(@RequestBody DishPostDTO dishPostDTO) {
        return itemService.createDish(dishPostDTO);
    }

    @GetMapping("ingredients")
    public List<Ingredient> getIngredients() {
        return itemService.getIngredients();
    }

    @PutMapping("ingredients/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long ingredientId, @RequestBody IngredientDTO updatedIngredient) {
        return itemService.updateIngredient(ingredientId, updatedIngredient);
    }
}
