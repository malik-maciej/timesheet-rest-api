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

    Page<UserDto> getFilteredUsers(UserFilterForm userFilterForm, Pageable pageable);

    User createUser(UserCreateForm createForm);

    boolean updateUser(UUID uuid, UserCreateForm createForm);

    void deleteUser(UUID uuid);

    User getUserByUuid(UUID uuid);

    boolean existsUserByLogin(String userLogin);
}