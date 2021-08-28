package com.k4rnaj1k.bestcafe.model;

import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
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
    private Long id;

    @OneToMany
//    @JsonIgnoreProperties("order")
    private List<DishItem> dishes = new ArrayList<>();

    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.SENT;
    }

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        List<DishItem> dishes = new ArrayList<>();
        orderDTO.getDishes().forEach(dishItemDTO -> {
            DishItem dishItem = new DishItem();

            Dish dish = new Dish();
            dish.setId(dishItemDTO.getDishId());
            dishItem.setDish(dish);

            dishItem.setAmount(dishItemDTO.getAmount());

            List<Ingredient> excluded = new ArrayList<>();
            dishItemDTO.getExcludedIngredients().forEach(ingredientId -> {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(ingredientId);
                excluded.add(ingredient);
            });
            dishItem.setExcluded(excluded);

            dishes.add(dishItem);
        });
        order.setDishes(dishes);

        List<DrinkItem> drinks = new ArrayList<>();
        orderDTO.getDrinks().forEach(drinkItemDTO -> {
            Drink drink = new Drink();
            drink.setId(drinkItemDTO.getDrinkId());

            DrinkItem drinkItem = new DrinkItem();
            drinkItem.setDrink(drink);
            drinkItem.setAmount(drinkItemDTO.getAmount());
        });
        order.setDrinks(drinks);
        return order;
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
    private List<DrinkItem> drinks = new ArrayList<>();
}
