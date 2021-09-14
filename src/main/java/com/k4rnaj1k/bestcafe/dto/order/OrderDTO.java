package com.k4rnaj1k.bestcafe.dto.order;

import java.util.List;

public record OrderDTO(
        List<DishOrderDTO> dishes,
        List<DrinkOrderDTO> drinks) {
}
