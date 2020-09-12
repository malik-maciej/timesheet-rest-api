package com.malik.restapi.controller;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.mapper.UserMapper;
import com.malik.restapi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
class UserController {

    interface Routes {
        String ROOT = "/users";
        String USER_BY_UUID = ROOT + "/{uuid}";
        String FILTER = ROOT + "/filter";
    }

    private final UserService userService;
    private final UserMapper userMapper;

    UserController(final UserService userService, final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(Routes.ROOT)
    @ResponseStatus(value = HttpStatus.OK)
    List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(Routes.FILTER)
    @ResponseStatus(value = HttpStatus.OK)
    Page<UserDto> getFilteredUsers(@RequestBody @Valid final UserFilterForm filterForm, final Pageable pageable) {
        return userService.getFilteredUsers(filterForm, pageable);
    }

    @PostMapping(Routes.ROOT)
    ResponseEntity<?> createUser(@RequestBody @Valid final UserCreateForm createForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors() || userService.existsUserByLogin(createForm.getLogin())) {
            return ResponseEntity.badRequest().build();
        }

        final UserDto result = userMapper.userToUserDto(userService.createUser(createForm));
        return ResponseEntity.created(URI.create("/" + result.getUuid())).body(result);
    }

    @PutMapping(Routes.USER_BY_UUID)
    ResponseEntity<?> updateUser(@PathVariable final UUID uuid, @RequestBody @Valid final UserCreateForm createForm, final BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Complete all fields properly");
        }

        if (userService.updateUser(uuid, createForm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(Routes.USER_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable final UUID uuid) {
        userService.deleteUser(uuid);
    }
}