package com.malik.restapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "uuid", nullable = false, unique = true)
    private final UUID uuid = UUID.randomUUID();
}