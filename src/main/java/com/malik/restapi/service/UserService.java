package com.malik.restapi.service;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    List<UserDto> getAllUsers();

    Page<UserDto> getFilteredUsers(final UserFilterForm userFilterForm, final Pageable pageable);

    User createUser(final UserCreateForm createForm);

    void updateUser(final UUID uuid, final UserCreateForm createForm);

    void deleteUser(final UUID uuid);

    User getUserByUuid(final UUID uuid);

    boolean existsUserByLogin(final String userLogin);
}