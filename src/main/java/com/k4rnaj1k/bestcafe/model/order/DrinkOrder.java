package com.k4rnaj1k.bestcafe.model.order;

import com.k4rnaj1k.bestcafe.dto.order.DrinkOrderDTO;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import lombok.*;
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
        drink.setId(drinkOrderDTO.getDrinkId());

        drinkOrder.setDrink(drink);
        drinkOrder.setAmount(drinkOrderDTO.getAmount());
        return drinkOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DrinkOrder that = (DrinkOrder) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1380863847;
    }
}
