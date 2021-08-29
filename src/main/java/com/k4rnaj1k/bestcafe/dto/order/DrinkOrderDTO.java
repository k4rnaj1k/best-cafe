package com.k4rnaj1k.bestcafe.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DrinkOrderDTO {
    @JsonProperty(value = "drink_id", index = 0)
    private Long drinkId;
    private Long amount;
}
