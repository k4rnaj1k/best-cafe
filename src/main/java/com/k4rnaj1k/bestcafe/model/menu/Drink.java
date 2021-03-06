package com.k4rnaj1k.bestcafe.model.menu;

import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "drinks")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double price;

    public static Drink fromDrinkDTO(DrinkPostDTO drinkPostDTO) {
        Drink drink = new Drink();
        drink.setName(drinkPostDTO.name());
        drink.setPrice(drinkPostDTO.price());
        return drink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Drink drink = (Drink) o;

        return Objects.equals(id, drink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
