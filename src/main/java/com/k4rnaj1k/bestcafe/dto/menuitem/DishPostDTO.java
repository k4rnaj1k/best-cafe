package com.k4rnaj1k.bestcafe.dto.menuitem;

import lombok.Data;

import java.util.List;

@Data
public class DishPostDTO {
    private String name;
    private List<Long> ingredients;
    private Double price;
}
