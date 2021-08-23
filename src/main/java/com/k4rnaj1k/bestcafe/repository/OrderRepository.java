package com.k4rnaj1k.bestcafe.repository;

import com.k4rnaj1k.bestcafe.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
