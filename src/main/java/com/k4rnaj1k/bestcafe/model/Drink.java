package com.k4rnaj1k.bestcafe.model;

import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
@Data
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    public static Drink fromDrinkDTO(DrinkPostDTO drinkPostDTO) {
        Drink drink = new Drink();
        drink.setName(drinkPostDTO.getName());
        drink.setPrice(drinkPostDTO.getPrice());
        return drink;
    }
}
