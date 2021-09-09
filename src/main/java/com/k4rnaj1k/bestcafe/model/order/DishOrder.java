package com.k4rnaj1k.bestcafe.model.order;

import com.k4rnaj1k.bestcafe.dto.order.DishOrderDTO;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dish_orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dish dish;

    private Long amount;

    @ManyToMany
    @JoinTable(name = "excluded_dish_ingredients", joinColumns = @JoinColumn(name = "dish_order_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @ToString.Exclude
    private List<Ingredient> excluded = new ArrayList<>();

    public static DishOrder fromDTO(DishOrderDTO dishOrderDTO) {
        DishOrder dishOrder = new DishOrder();
        Dish dish = new Dish();
        dish.setId(dishOrderDTO.dishId());
        dishOrder.setDish(dish);

        dishOrder.setAmount(dishOrderDTO.amount());

        List<Ingredient> excluded = new ArrayList<>();
        if (dishOrderDTO.excludedIngredients() != null) {
            dishOrderDTO.excludedIngredients().forEach(ingredientId -> {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                excluded.add(ingredient);
            });
        }
        dishOrder.setExcluded(excluded);
        return dishOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishOrder dishOrder = (DishOrder) o;
        return Objects.equals(id, dishOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
