package com.k4rnaj1k.bestcafe.repository;

import com.k4rnaj1k.bestcafe.model.DishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishItemRepository extends JpaRepository<DishItem, Long> {
    Optional<DishItem> findByDishId(Long dishId);
}
