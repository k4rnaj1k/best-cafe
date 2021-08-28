package com.k4rnaj1k.bestcafe.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private List<DishItemDTO> dishes;
    private List<DrinkItemDTO> drinks;
}
