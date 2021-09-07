package com.k4rnaj1k.bestcafe.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.k4rnaj1k.bestcafe.model.menu.Drink;
import lombok.Data;
import org.springframework.data.annotation.Reference;

@Data
public class DrinkOrderDTO {
    @JsonProperty(value = "drink_id", index = 0)
    @Reference(Drink.class)
    private Long drinkId;
    private Long amount;
}
