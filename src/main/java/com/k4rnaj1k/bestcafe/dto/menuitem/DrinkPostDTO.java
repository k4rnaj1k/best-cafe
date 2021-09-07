package com.k4rnaj1k.bestcafe.dto.menuitem;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
public class DrinkPostDTO {
    @NotBlank
    private String name;
    @DecimalMin("1")
    private Double price;
}
