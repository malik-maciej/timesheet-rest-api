package com.malik.restapi.model;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Immutable
@Table(name = "projects_view")
public class ProjectView {

    @Id
    @Column(name = "id")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "budget")
    private BigDecimal budget;

    @Column(name = "budget_percentage")
    private Double budgetPercentage;

    @Column(name = "budget_exceeded")
    private Boolean budgetExceeded;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users;
}