package com.k4rnaj1k.bestcafe.repository.menu;

import com.k4rnaj1k.bestcafe.model.menu.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    boolean existsByName(String name);
}
