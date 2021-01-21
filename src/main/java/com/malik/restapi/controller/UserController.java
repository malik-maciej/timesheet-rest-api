package com.malik.restapi.controller;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.mapper.UserMapper;
import com.malik.restapi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    UserController(final UserService userService, final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(Routes.ROOT)
    List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(Routes.FILTER)
    Page<UserDto> getFilteredUsers(@RequestBody @Valid final UserFilterForm filterForm, final Pageable pageable) {
        return userService.getFilteredUsers(filterForm, pageable);
    }

    @PostMapping(Routes.ROOT)
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@RequestBody @Valid final UserCreateForm createForm) {
        return userMapper.entityToDto(userService.createUser(createForm));
    }

    @PutMapping(Routes.USER_BY_UUID)
    @ResponseStatus(HttpStatus.OK)
    void updateUser(@PathVariable final UUID uuid, @RequestBody @Valid final UserCreateForm createForm) {
        userService.updateUser(uuid, createForm);
    }

    @DeleteMapping(Routes.USER_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable final UUID uuid) {
        userService.deleteUser(uuid);
    }

    private static class Routes {
        static final String ROOT = "/users";
        static final String USER_BY_UUID = ROOT + "/{uuid}";
        static final String FILTER = ROOT + "/filter";
    }
}