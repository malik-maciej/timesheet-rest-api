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
    @Query("select distinct p from projects p left join fetch p.users left join fetch p.worktimes")
    List<Project> findAll();

    Optional<Project> findByName(String name);

    @Query("select p from projects p" +
            " left join fetch p.worktimes w" +
            " left join fetch p.users u" +
            " where p.uuid=:uuid")
    Optional<Project> findByUuid(@Param(("uuid")) UUID uuid);

    boolean existsByName(String name);
}