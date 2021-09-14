package com.k4rnaj1k.bestcafe.repository.menu;

import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByName(String name);

    Ingredient getByName(String name);

}
