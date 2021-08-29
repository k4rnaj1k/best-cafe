package com.k4rnaj1k.bestcafe.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
//    @JsonIgnoreProperties("order")
    private List<DishOrder> dishes = new ArrayList<>();

    private OrderStatus status;

    @Column(name="created_at")
    private Instant createdAt;

    @Column(name="updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnore
    private User user;

    private Double price;

    public Order() {
        this.status = OrderStatus.SENT;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        List<DishOrder> dishes = new ArrayList<>();
        orderDTO.getDishes().forEach(dishItemDTO -> {
            DishOrder dishOrder = DishOrder.fromDTO(dishItemDTO);
            dishes.add(dishOrder);
        });
        order.setDishes(dishes);
        List<DrinkOrder> drinks = new ArrayList<>();
        orderDTO.getDrinks().forEach(drinkItemDTO -> {
            DrinkOrder drinkOrder = DrinkOrder.fromDTO(drinkItemDTO);
            drinks.add(drinkOrder);
        });
        order.setDrinks(drinks);
        return order;
    }

    public enum OrderStatus {
        SENT(1),
        ACCEPTED(2),
        DONE(3);
        private final int value;

        public int getValue() {
            return value;
        }

        OrderStatus(int value) {
            this.value = value;
        }
    }

    @OneToMany
    private List<DrinkOrder> drinks = new ArrayList<>();
}
