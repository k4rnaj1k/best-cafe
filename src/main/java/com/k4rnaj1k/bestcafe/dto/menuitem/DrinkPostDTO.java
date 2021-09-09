package com.k4rnaj1k.bestcafe.dto.menuitem;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public record DrinkPostDTO(
        @NotBlank
        String name,
        @DecimalMin("1")
        Double price) {
}
