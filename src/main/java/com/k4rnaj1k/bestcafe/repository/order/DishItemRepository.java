package com.k4rnaj1k.bestcafe.repository.order;

import com.k4rnaj1k.bestcafe.model.order.DishOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishItemRepository extends JpaRepository<DishOrder, Long> {
    Optional<DishOrder> findByDishId(Long dishId);
}
