package com.k4rnaj1k.bestcafe.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DishOrderDTO {
    @JsonProperty(value = "dish_id", index = 0)
    private Long dishId;
    private Long amount;
    private List<Long> excludedIngredients;
}
