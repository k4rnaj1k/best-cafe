package com.k4rnaj1k.bestcafe.model.menu;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @DecimalMin("1")
    private Double price;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    public static Dish fromPostDTO(DishPostDTO dishPostDTO) {
        Dish dish = new Dish();
        dish.setName(dishPostDTO.name());
        List<Ingredient> ingredients = new ArrayList<>();
        dish.setPrice(dishPostDTO.price());
        return dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dish dish = (Dish) o;

        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return 161829241;
    }
}
