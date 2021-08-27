package com.k4rnaj1k.bestcafe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.k4rnaj1k.bestcafe.configuration.Views;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
@Data
@JsonView(Views.Get.class)
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.PostDrink.class)
    private String name;
}
