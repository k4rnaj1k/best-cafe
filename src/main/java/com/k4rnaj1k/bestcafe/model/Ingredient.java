package com.k4rnaj1k.bestcafe.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "ingredients")
@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    @Getter
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Dish> dishes;
}
