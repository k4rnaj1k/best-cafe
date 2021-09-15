package com.k4rnaj1k.bestcafe.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import org.springframework.data.annotation.Reference;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public record DishOrderDTO(
        @JsonProperty(value = "dish_id", index = 0)
        @Reference(Dish.class)
        Long dishId,

        @Min(1L)
        Long amount,

        @NotNull
        List<Long> excludedIngredients) {
}
