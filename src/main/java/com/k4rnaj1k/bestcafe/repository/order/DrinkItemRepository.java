package com.k4rnaj1k.bestcafe.repository.order;

import com.k4rnaj1k.bestcafe.model.order.DrinkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkItemRepository extends JpaRepository<DrinkOrder, Long> {
}
