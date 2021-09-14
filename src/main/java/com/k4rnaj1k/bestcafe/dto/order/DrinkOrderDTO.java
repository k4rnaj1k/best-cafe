package com.k4rnaj1k.bestcafe.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import org.springframework.data.annotation.Reference;

public record DrinkOrderDTO(
        @JsonProperty(value = "drink_id", index = 0)
        @Reference(Drink.class)
        Long drinkId,
        Long amount) {
}
