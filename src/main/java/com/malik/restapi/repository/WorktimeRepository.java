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

    @Query("select w from worktimes w left join fetch w.user left join fetch w.project where w.user=:user")
    List<Worktime> findAllByUser(@Param("user") User user);

    Optional<Worktime> findByUuid(UUID uuid);
}