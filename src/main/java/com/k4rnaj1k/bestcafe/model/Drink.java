package com.k4rnaj1k.bestcafe.model;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
