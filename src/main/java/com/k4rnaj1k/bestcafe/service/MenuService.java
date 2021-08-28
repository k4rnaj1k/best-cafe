package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.exception.CafeException;
import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.repository.DishRepository;
import com.k4rnaj1k.bestcafe.repository.DrinkRepository;
import com.k4rnaj1k.bestcafe.repository.IngredientRepository;
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

    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredientRepository.existsByName(ingredient.getName()))
            throw CafeException.ingredientAlreadyExists(ingredient.getName());
        return ingredientRepository.save(ingredient);
    }

    public Dish createDish(Dish dish) {
        dish.getIngredients().forEach(ingredient -> {
            Long id = ingredient.getId();
            ingredient.setName(ingredientRepository.findById(id)
                    .orElseThrow(() -> CafeException.ingredientDoesntExist(id)).getName());
        });
        return dishRepository.save(dish);
    }

    public Ingredient updateIngredient(Long ingredientId, Ingredient ingredient) {
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw CafeException.ingredientAlreadyExists(ingredient.getName());
        }
        Ingredient ingredientFromDb = ingredientRepository.findById(ingredientId).orElseThrow(() -> CafeException.ingredientDoesntExist(ingredientId));
        ingredientFromDb.setName(ingredient.getName());
        return ingredientRepository.save(ingredientFromDb);
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public void removeIngredientById(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
