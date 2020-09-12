package com.malik.restapi.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "projects")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Project extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> users = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Worktime> worktimes = new HashSet<>();

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public Set<Worktime> getWorktimes() {
        return Collections.unmodifiableSet(worktimes);
    }

    public void addUser(final User user) {
        users.add(user);
        user.addProject(this);
    }

    public void removeUser(final User user) {
        users.remove(user);
        user.removeProject(this);
    }

    public void addWorktime(final Worktime worktime) {
        worktimes.add(worktime);
        worktime.setProject(this);
    }

    public void removeWorktime(final Worktime worktime) {
        worktimes.remove(worktime);
        worktime.setProject(null);
    }
}