package com.k4rnaj1k.bestcafe.model.auth;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

}
