package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.exception.CafeExceptionUtils;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import com.k4rnaj1k.bestcafe.repository.menu.DishRepository;
import com.k4rnaj1k.bestcafe.repository.menu.DrinkRepository;
import com.k4rnaj1k.bestcafe.repository.menu.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuService {
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;

    public MenuService(DishRepository dishRepository, DrinkRepository drinkRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional(readOnly = true)
    public List<Drink> getDrinks() {
        return drinkRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = Ingredient.fromDTO(ingredientDTO);
        if (ingredientRepository.existsByName(ingredient.getName()))
            throw CafeExceptionUtils.ingredientAlreadyExists(ingredient.getName());
        return ingredientRepository.save(ingredient);
    }

    public Dish createDish(DishPostDTO dishPostDTO) {
        if (dishRepository.existsByName(dishPostDTO.name())) {
            throw CafeExceptionUtils.dishAlreadyExists(dishPostDTO.name());
        }
        Dish dish = Dish.fromPostDTO(dishPostDTO);
        dish.setIngredients(getDishIngredientsFromIds(dishPostDTO.ingredients()));
        return dishRepository.save(dish);
    }

    public Ingredient updateIngredient(Long ingredientId, IngredientDTO ingredientDTO) {
        if (ingredientRepository.existsByName(ingredientDTO.name())) {
            throw CafeExceptionUtils.ingredientAlreadyExists(ingredientDTO.name());
        }
        Ingredient ingredientFromDb = getIngredient(ingredientId);
        ingredientFromDb.setName(ingredientDTO.name());
        return ingredientRepository.save(ingredientFromDb);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public void removeIngredientById(Long ingredientId) {
        Ingredient ingredient = getIngredient(ingredientId);
        if (ingredient.getDishes().size() == 0) {
            ingredientRepository.deleteById(ingredientId);
        } else {
            ingredient.getDishes().forEach(dish -> {
                if (dish.getIngredients().size() == 1)
                    throw CafeExceptionUtils.dishWithOneIngredient(dish.getId());
            });
            ingredientRepository.delete(ingredient);
        }
    }

    private Ingredient getIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(() -> CafeExceptionUtils.ingredientDoesntExist(ingredientId));
    }

    @Transactional(readOnly = true)
    public Dish getDishWithId(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> CafeExceptionUtils.dishDoesntExist(dishId));
    }

    public Drink createDrink(DrinkPostDTO drinkPostDTO) {
        if (drinkRepository.existsByName(drinkPostDTO.name())) {
            throw CafeExceptionUtils.drinkExistsException(drinkPostDTO.name());
        }
        Drink drink = Drink.fromDrinkDTO(drinkPostDTO);
        return drinkRepository.save(drink);
    }

    public void removeDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> CafeExceptionUtils.dishDoesntExist(dishId));
        dishRepository.delete(dish);
    }

    public Dish updateDish(Long dishId, DishPostDTO dishPostDTO) {
        Dish dish = getDishWithId(dishId);
        dish.setName(dishPostDTO.name());
        List<Ingredient> ingredients = getDishIngredientsFromIds(dishPostDTO.ingredients());
        dish.setIngredients(ingredients);
        return dish;
    }

    private List<Ingredient> getDishIngredientsFromIds(List<Long> ingredientIds) {
        return ingredientRepository.findAllById(ingredientIds);
    }

    public IngredientDTO getIngredientById(Long id) {
        Ingredient ingredient = getIngredient(id);
        return new IngredientDTO(ingredient.getName());
    }

    public void removeDrinkById(Long drinkId) {
        drinkRepository.deleteById(drinkId);
    }
}
