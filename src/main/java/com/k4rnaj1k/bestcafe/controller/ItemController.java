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

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemController {
    private final MenuService itemService;

    public ItemController(MenuService itemService) {
        this.itemService = itemService;
    }

    //region Ingredients
    @DeleteMapping(Routes.INGREDIENTS + "/{id}")
    public void removeIngredient(@PathVariable("id") Long ingredientId) {
        itemService.removeIngredientById(ingredientId);
    }

    @GetMapping(Routes.INGREDIENTS)
    public List<Ingredient> getIngredients() {
        return itemService.getIngredients();
    }

    @PutMapping(Routes.INGREDIENTS + "/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long ingredientId, @RequestBody IngredientDTO updatedIngredient) {
        return itemService.updateIngredient(ingredientId, updatedIngredient);
    }

    @PostMapping(Routes.INGREDIENTS)
    public Ingredient createIngredient(@RequestBody IngredientDTO ingredientDto) {
        return itemService.createIngredient(ingredientDto);
    }
    //endregion


    //region Dishes
    @DeleteMapping(Routes.DISHES + "/{id}")
    public void removeDish(@PathVariable("id") Long dishId) {
        itemService.removeDishById(dishId);
    }

    @PutMapping(Routes.DISHES + "/{id}")
    public Dish updateDish(@PathVariable("id") Long dishId, @RequestBody DishPostDTO dishPostDTO) {
        return itemService.updateDish(dishId, dishPostDTO);
    }

    @GetMapping(Routes.DISHES)
    public List<Dish> getDishes() {
        return itemService.getDishes();
    }

    @GetMapping(Routes.DISHES + "/{id}")
    public Dish getDishes(@PathVariable("id") Long dishId) {
        return itemService.getDishWithId(dishId);
    }

    @PostMapping(Routes.DISHES)
    public Dish createDish(@RequestBody DishPostDTO dishPostDTO) {
        return itemService.createDish(dishPostDTO);
    }


    //endregion

    //region Drinks
    @GetMapping(Routes.DRINKS)
    public List<Drink> getDrinks() {
        return itemService.getDrinks();
    }

    @PostMapping(Routes.DRINKS)
    public Drink createDrink(@RequestBody @Valid DrinkPostDTO drinkPostDTO) {
        return itemService.createDrink(drinkPostDTO);
    }
    //endregion
}
