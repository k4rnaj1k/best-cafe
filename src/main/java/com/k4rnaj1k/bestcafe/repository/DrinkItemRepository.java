package com.k4rnaj1k.bestcafe.repository;

import com.k4rnaj1k.bestcafe.model.DrinkItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkItemRepository extends JpaRepository<DrinkItem, Long> {
}
