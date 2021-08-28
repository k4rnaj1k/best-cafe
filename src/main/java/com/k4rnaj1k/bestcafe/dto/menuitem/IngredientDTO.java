package com.k4rnaj1k.bestcafe.dto.menuitem;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IngredientDTO {
    @NotBlank
    private String name;
}
