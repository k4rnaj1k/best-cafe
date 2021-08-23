package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties("order")
    private List<DishItem> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties("order")
    private List<DrinkItem> drinks = new ArrayList<>();
}
