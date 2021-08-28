package com.k4rnaj1k.bestcafe.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class DishItemDTO {
    private Long dishId;
    private Long amount;
    private List<Long> excludedIngredients;
}
