package com.k4rnaj1k.bestcafe.repository.menu;

import com.k4rnaj1k.bestcafe.model.menu.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    boolean existsByName(String name);
}
