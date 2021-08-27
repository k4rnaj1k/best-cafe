package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Get.class)
    private Long id;

    @OneToMany
    @JsonView(Views.PostOrder.class)
//    @JsonIgnoreProperties("order")
    private List<DishItem> dishes = new ArrayList<>();

    @JsonView(Views.PutOrder.class)
    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.SENT;
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
    @JsonView(Views.PostOrder.class)
    private List<DrinkItem> drinks = new ArrayList<>();
}
