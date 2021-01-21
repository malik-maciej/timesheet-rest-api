package com.malik.restapi.service.impl;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.exception.NonUniqueException;
import com.malik.restapi.exception.NotFoundException;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.mapper.UserMapper;
import com.malik.restapi.model.User;
import com.malik.restapi.repository.UserRepository;
import com.malik.restapi.service.UserService;
import com.malik.restapi.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserServiceImpl(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getFilteredUsers(final UserFilterForm userFilterForm, final Pageable pageable) {
        final UserSpecification specification = new UserSpecification(userFilterForm);
        return userRepository.findAll(specification, pageable)
                .map(userMapper::entityToDto);
    }

    @Override
    public User createUser(final UserCreateForm createForm) {
        if (existsUserByLogin(createForm.getLogin())) {
            throw new NonUniqueException(ErrorMessages.USER_LOGIN_ALREADY_EXISTS);
        }
        return userRepository.save(userMapper.formToEntity(createForm));
    }

    @Override
    public void updateUser(final UUID uuid, final UserCreateForm createForm) {
        final User user = getUserByUuid(uuid);
        if (isFalse(isLoginAvailable(user.getLogin(), createForm.getLogin()))) {
            throw new NonUniqueException(ErrorMessages.USER_LOGIN_ALREADY_EXISTS);
        }

        userMapper.fromUserCreateForm(createForm, user);
        userRepository.save(user);
    }

    private boolean isLoginAvailable(final String login, final String newLogin) {
        return login.equals(newLogin) || isFalse(existsUserByLogin(newLogin));
    }

    @Override
    public void deleteUser(final UUID uuid) {
        userRepository.delete(getUserByUuid(uuid));
    }

    @Override
    public User getUserByUuid(final UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public boolean existsUserByLogin(final String userLogin) {
        return userRepository.existsByLogin(userLogin);
    }

    static class ErrorMessages {
        static final String USER_NOT_FOUND = "User with given UUID not found";
        static final String USER_LOGIN_ALREADY_EXISTS = "User with given login is already exists";
    }
}