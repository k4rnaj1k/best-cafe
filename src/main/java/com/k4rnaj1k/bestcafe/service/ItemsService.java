package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.model.Dish;
import com.k4rnaj1k.bestcafe.model.Drink;
import com.k4rnaj1k.bestcafe.repository.DishRepository;
import com.k4rnaj1k.bestcafe.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {
    private final DishRepository dishRepository;
    private final DrinkRepository drinkRepository;

    @Autowired
    public ItemsService(DishRepository dishRepository, DrinkRepository drinkRepository) {
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
    }

    public List<Drink> getDrinks() {
        return drinkRepository.findAll();
    }

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }
}
