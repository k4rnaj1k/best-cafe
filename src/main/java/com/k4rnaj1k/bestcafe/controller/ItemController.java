package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private final MenuService itemService;

    public ItemController(MenuService itemService) {
        this.itemService = itemService;
    }

    @DeleteMapping(Routes.INGREDIENTS+"/{id}")
    public void removeIngredient(@PathVariable("id") Long ingredientId) {
        itemService.removeIngredientById(ingredientId);
    }

    @DeleteMapping(Routes.DISHES + "/{id}")
    public void removeDish(@PathVariable("id") Long dishId){
        itemService.removeDishById(dishId);
    }

    @PostMapping(Routes.INGREDIENTS)
    public Ingredient createIngredient(@RequestBody IngredientDTO ingredientDto) {
        return itemService.createIngredient(ingredientDto);
    }

    @PostMapping(Routes.DRINKS)
    public Drink createDrink(@RequestBody DrinkPostDTO drinkPostDTO){
        return itemService.createDrink(drinkPostDTO);
    }

    @GetMapping(Routes.DRINKS)
    public List<Drink> getDrinks() {
        return itemService.getDrinks();
    }

    @GetMapping(Routes.DISHES)
    public List<Dish> getDishes() {
        return itemService.getDishes();
    }

    @GetMapping(Routes.DISHES+"/{id}")
    public Dish getDishes(@PathVariable("id") Long dishId) {
        return itemService.getDishWithId(dishId);
    }

    @PostMapping(Routes.DISHES)
    public Dish createDish(@RequestBody DishPostDTO dishPostDTO) {
        return itemService.createDish(dishPostDTO);
    }

    @GetMapping(Routes.INGREDIENTS)
    public List<Ingredient> getIngredients() {
        return itemService.getIngredients();
    }

    @PutMapping(Routes.INGREDIENTS + "/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long ingredientId, @RequestBody IngredientDTO updatedIngredient) {
        return itemService.updateIngredient(ingredientId, updatedIngredient);
    }
}
