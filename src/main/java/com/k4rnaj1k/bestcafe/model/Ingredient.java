package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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
}
