package com.malik.restapi.controller;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.mapper.UserMapper;
import com.malik.restapi.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/filter")
    @ResponseStatus(value = HttpStatus.OK)
    Page<UserDto> getFilteredUsers(@RequestBody @Valid UserFilterForm filterForm, Pageable pageable) {
        return userService.getFilteredUsers(filterForm, pageable);
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody @Valid UserCreateForm createForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || userService.existsUserByLogin(createForm.getLogin())) {
            return ResponseEntity.badRequest().build();
        }

        UserDto result = userMapper.userToUserDto(userService.createUser(createForm));
        return ResponseEntity.created(URI.create("/" + result.getUuid())).body(result);
    }

    @PutMapping("/{uuid}")
    ResponseEntity<?> updateUser(@PathVariable UUID uuid, @RequestBody @Valid UserCreateForm createForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Complete all fields properly");
        }

        if (userService.updateUser(uuid, createForm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable UUID uuid) {
        userService.deleteUser(uuid);
    }
}