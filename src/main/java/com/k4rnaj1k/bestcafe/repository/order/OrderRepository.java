package com.k4rnaj1k.bestcafe.repository.order;

import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
