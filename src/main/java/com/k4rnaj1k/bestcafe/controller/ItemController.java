package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@OpenAPIDefinition(tags = {@Tag(name = "ingredients"), @Tag(name = "drinks"), @Tag(name = "dishes")})
public class ItemController {
    private final MenuService itemService;

    public ItemController(MenuService itemService) {
        this.itemService = itemService;
    }

    //region Ingredients
    @Tag(name = "ingredients")
    @DeleteMapping(Routes.INGREDIENTS + "/{id}")
    public void removeIngredient(@PathVariable("id") Long ingredientId) {
        itemService.removeIngredientById(ingredientId);
    }

    @Tag(name = "ingredients")
    @Transactional(readOnly = true)
    @GetMapping(Routes.INGREDIENTS)
    public List<Ingredient> getIngredients() {
        return itemService.getIngredients();
    }

    @Tag(name = "ingredients")
    @PutMapping(Routes.INGREDIENTS + "/{id}")
    public Ingredient updateIngredient(@PathVariable("id") Long ingredientId, @RequestBody @Valid IngredientDTO updatedIngredient) {
        return itemService.updateIngredient(ingredientId, updatedIngredient);
    }

    @Tag(name = "ingredients")
    @PostMapping(Routes.INGREDIENTS)
    public Ingredient createIngredient(@RequestBody @Valid IngredientDTO ingredientDto) {
        return itemService.createIngredient(ingredientDto);
    }
    //endregion


    //region Dishes
    @Tag(name = "dishes")
    @DeleteMapping(Routes.DISHES + "/{id}")
    public void removeDish(@PathVariable("id") Long dishId) {
        itemService.removeDishById(dishId);
    }

    @Tag(name = "dishes")
    @PutMapping(Routes.DISHES + "/{id}")
    public Dish updateDish(@PathVariable("id") Long dishId, @RequestBody @Valid DishPostDTO dishPostDTO) {
        return itemService.updateDish(dishId, dishPostDTO);
    }

    @Tag(name = "dishes")
    @Transactional(readOnly = true)
    @GetMapping(Routes.DISHES)
    public List<Dish> getDishes() {
        return itemService.getDishes();
    }

    @Tag(name = "dishes")
    @Transactional(readOnly = true)
    @GetMapping(Routes.DISHES + "/{id}")
    public Dish getDishes(@PathVariable("id") Long dishId) {
        return itemService.getDishWithId(dishId);
    }

    @Tag(name = "dishes")
    @PostMapping(Routes.DISHES)
    public Dish createDish(@RequestBody @Valid DishPostDTO dishPostDTO) {
        return itemService.createDish(dishPostDTO);
    }

    //endregion

    //region Drinks
    @Tag(name = "drinks")
    @Transactional(readOnly = true)
    @GetMapping(Routes.DRINKS)
    public List<Drink> getDrinks() {
        return itemService.getDrinks();
    }

    @Tag(name = "drinks")
    @PostMapping(Routes.DRINKS)
    public Drink createDrink(@RequestBody @Valid DrinkPostDTO drinkPostDTO) {
        return itemService.createDrink(drinkPostDTO);
    }
    //endregion
}
