package com.malik.restapi.model;

import com.malik.restapi.util.UserType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends AbstractEntity {

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "salary_per_hour", nullable = false)
    private BigDecimal salaryPerHour;

    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Worktime> worktimes = new HashSet<>();

    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }

    void addProject(Project project) {
        projects.add(project);
    }

    void removeProject(Project project) {
        projects.remove(project);
    }

    public Set<Worktime> getWorktimes() {
        return Collections.unmodifiableSet(worktimes);
    }

    public void addWorktime(Worktime worktime) {
        worktimes.add(worktime);
        worktime.setUser(this);
    }

    public void removeWorktime(Worktime worktime) {
        worktimes.remove(worktime);
        worktime.setUser(null);
    }
}