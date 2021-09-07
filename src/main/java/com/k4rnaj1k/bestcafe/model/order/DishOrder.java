package com.k4rnaj1k.bestcafe.model.order;

import com.k4rnaj1k.bestcafe.dto.order.DishOrderDTO;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish_orders")
@Data
public class DishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dish dish;

    private Long amount;

    @ManyToMany
    @JoinTable(name = "excluded_dish_ingredients", joinColumns = @JoinColumn(name = "dish_order_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
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
}
