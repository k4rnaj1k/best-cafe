package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.exception.CafeException;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import com.k4rnaj1k.bestcafe.repository.menu.DishRepository;
import com.k4rnaj1k.bestcafe.repository.menu.DrinkRepository;
import com.k4rnaj1k.bestcafe.repository.menu.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;

    public MenuService(DishRepository dishRepository, DrinkRepository drinkRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Drink> getDrinks() {
        return drinkRepository.findAll();
    }

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = Ingredient.fromDTO(ingredientDTO);
        if (ingredientRepository.existsByName(ingredient.getName()))
            throw CafeException.ingredientAlreadyExists(ingredient.getName());
        return ingredientRepository.save(ingredient);
    }

    public Dish createDish(DishPostDTO dishPostDTO) {
        Dish dish = Dish.fromPostDTO(dishPostDTO);
        dish.setIngredients(getDishIngredientsFromIds(dishPostDTO.getIngredients()));
        return dishRepository.save(dish);
    }

    public Ingredient updateIngredient(Long ingredientId, IngredientDTO ingredientDTO) {
        if (ingredientRepository.existsByName(ingredientDTO.getName())) {
            throw CafeException.ingredientAlreadyExists(ingredientDTO.getName());
        }
        Ingredient ingredientFromDb = ingredientRepository.findById(ingredientId).orElseThrow(() -> CafeException.ingredientDoesntExist(ingredientId));
        ingredientFromDb.setName(ingredientDTO.getName());
        return ingredientRepository.save(ingredientFromDb);
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public void removeIngredientById(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> CafeException.ingredientDoesntExist(ingredientId));
        if (ingredient.getDishes().size() == 0) {
            ingredientRepository.deleteById(ingredientId);
        } else {
            ingredient.getDishes().forEach(dish -> {
                if (dish.getIngredients().size() == 1)
                    throw CafeException.dishWithOneIngredient(dish.getId());
            });
            ingredientRepository.delete(ingredient);
        }
    }

    public Dish getDishWithId(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> CafeException.dishDoesntExist(dishId));
    }

    public Drink createDrink(DrinkPostDTO drinkPostDTO) {
        Drink drink = Drink.fromDrinkDTO(drinkPostDTO);
        return drinkRepository.save(drink);
    }

    public void removeDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> CafeException.dishDoesntExist(dishId));
        dishRepository.delete(dish);
    }

    public Dish updateDish(Long dishId,DishPostDTO dishPostDTO) {
        Dish dish = getDishWithId(dishId);
        dish.setName(dishPostDTO.getName());
        List<Ingredient> ingredients = getDishIngredientsFromIds(dishPostDTO.getIngredients());
        dish.setIngredients(ingredients);
        return dish;
    }

    private List<Ingredient> getDishIngredientsFromIds(List<Long> ingredientIds) {
        return ingredientRepository.findAllById(ingredientIds);
    }
}
