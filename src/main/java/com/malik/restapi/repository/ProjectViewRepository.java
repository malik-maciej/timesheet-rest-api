package com.malik.restapi.repository;

import com.malik.restapi.model.ProjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectViewRepository extends JpaRepository<ProjectView, Long>, JpaSpecificationExecutor<ProjectView> {

    @Override
    @EntityGraph(attributePaths = "users")
    Page<ProjectView> findAll(final Specification<ProjectView> spec, final Pageable pageable);
}