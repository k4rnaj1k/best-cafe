package com.k4rnaj1k.bestcafe.model.order;

import com.k4rnaj1k.bestcafe.dto.order.DrinkOrderDTO;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "drink_orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DrinkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Drink drink;

    private Long amount;

    public static DrinkOrder fromDTO(DrinkOrderDTO drinkOrderDTO) {
        DrinkOrder drinkOrder = new DrinkOrder();
        Drink drink = new Drink();
        drink.setId(drinkOrderDTO.drinkId());

        drinkOrder.setDrink(drink);
        drinkOrder.setAmount(drinkOrderDTO.amount());
        return drinkOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrinkOrder that = (DrinkOrder) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
