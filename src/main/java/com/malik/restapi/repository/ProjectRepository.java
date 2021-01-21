package com.malik.restapi.repository;

import com.malik.restapi.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @Query("SELECT DISTINCT p FROM projects p " +
            "LEFT JOIN FETCH p.users " +
            "LEFT JOIN FETCH p.worktimes")
    List<Project> findAll();

    Optional<Project> findByName(final String name);

    @Query("SELECT p FROM projects p " +
            "LEFT JOIN FETCH p.worktimes " +
            "LEFT JOIN FETCH p.users " +
            "WHERE p.uuid=:uuid")
    Optional<Project> findByUuid(@Param(("uuid")) final UUID uuid);

    boolean existsByName(final String name);
}