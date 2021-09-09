package com.k4rnaj1k.bestcafe.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ingredients")
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Dish> dishes;

    public static Ingredient fromDTO(IngredientDTO ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDto.name());
        return ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
