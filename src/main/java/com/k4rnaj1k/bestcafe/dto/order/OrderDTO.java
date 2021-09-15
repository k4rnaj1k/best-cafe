package com.k4rnaj1k.bestcafe.dto.order;

import javax.validation.constraints.NotNull;
import java.util.List;

public record OrderDTO(
        @NotNull List<DishOrderDTO> dishes,
        @NotNull List<DrinkOrderDTO> drinks) {
}
