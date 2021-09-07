package com.k4rnaj1k.bestcafe.dto.menuitem;

import lombok.Data;

import javax.validation.constraints.NotBlank;

public record IngredientDTO (
    @NotBlank
    String name){
}
