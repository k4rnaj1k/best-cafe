package com.k4rnaj1k.bestcafe.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import lombok.*;
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1847634289;
    }
}
