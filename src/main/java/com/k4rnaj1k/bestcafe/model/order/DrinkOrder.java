package com.k4rnaj1k.bestcafe.model.order;

import com.k4rnaj1k.bestcafe.dto.order.DrinkOrderDTO;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drink_orders")
@Data
public class DrinkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Drink drink;

    private Long amount;

    public static DrinkOrder fromDTO(DrinkOrderDTO drinkOrderDTO) {
        DrinkOrder drinkOrder = new DrinkOrder();
        Drink drink = new Drink();
        drink.setId(drinkOrderDTO.getDrinkId());

        drinkOrder.setDrink(drink);
        drinkOrder.setAmount(drinkOrderDTO.getAmount());
        return drinkOrder;
    }
}
