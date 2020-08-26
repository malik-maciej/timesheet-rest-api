package com.malik.restapi.repository;

import com.malik.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from users u" +
            " left join fetch u.projects p" +
            " left join fetch p.worktimes w" +
            " where u.uuid=:uuid")
    Optional<User> findByUuid(@Param("uuid") UUID uuid);

    boolean existsByLogin(String login);
}