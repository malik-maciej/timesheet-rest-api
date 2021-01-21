package com.malik.restapi.repository;

import com.malik.restapi.model.User;
import com.malik.restapi.model.Worktime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorktimeRepository extends JpaRepository<Worktime, Long> {

    @Query("SELECT w FROM worktimes w " +
            "LEFT JOIN FETCH w.user " +
            "LEFT JOIN FETCH w.project " +
            "WHERE w.user=:user")
    List<Worktime> findAllByUser(@Param("user") final User user);

    Optional<Worktime> findByUuid(final UUID uuid);
}