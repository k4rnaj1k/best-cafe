package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Table(name = "ingredients")
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.PostDish.class, Views.PostOrder.class})
    private Long id;

    @Column(unique = true)
    @NotBlank
    @JsonView(Views.PostIngredient.class)
    private String name;

//    @ManyToMany(mappedBy = "ingredients")
//    @JsonView(Views.AlterFull.class)
//    private List<Dish> dishes;
}
