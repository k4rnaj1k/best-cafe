package com.k4rnaj1k.bestcafe.dto.menuitem;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record DishPostDTO(
        @NotBlank
        String name,
        @NotNull
        List<Long> ingredients,
        @DecimalMin("1")
        Double price) {
}
