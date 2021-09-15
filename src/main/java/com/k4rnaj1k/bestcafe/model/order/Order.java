package com.k4rnaj1k.bestcafe.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @ToString.Exclude
    private List<DishOrder> dishes = new ArrayList<>();

    private OrderStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Order() {
        this.status = OrderStatus.SENT;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        List<DishOrder> dishes = new ArrayList<>();
        orderDTO.dishes().forEach(dishOrderDTO -> {
            DishOrder dishOrder = DishOrder.fromDTO(dishOrderDTO);
            dishes.add(dishOrder);
        });
        order.setDishes(dishes);
        List<DrinkOrder> drinks = new ArrayList<>();
        orderDTO.drinks().forEach(drinkOrderDTO -> {
            DrinkOrder drinkOrder = DrinkOrder.fromDTO(drinkOrderDTO);
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

    @JsonProperty("price")
    public Double getPrice() {
        Double price = 0d;
        for (DishOrder dish :
                dishes) {
            price += dish.getDish().getPrice();
        }

        for (DrinkOrder drink :
                drinks) {
            price += drink.getDrink().getPrice();
        }
        return price;
    }

    @OneToMany
    @ToString.Exclude
    private List<DrinkOrder> drinks = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
