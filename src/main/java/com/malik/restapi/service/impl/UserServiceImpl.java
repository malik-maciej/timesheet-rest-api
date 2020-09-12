package com.malik.restapi.service.impl;

import com.malik.restapi.dto.UserDto;
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
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getFilteredUsers(final UserFilterForm userFilterForm, final Pageable pageable) {
        final UserSpecification specification = new UserSpecification(userFilterForm);
        return userRepository.findAll(specification, pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public User createUser(final UserCreateForm createForm) {
        return userRepository.save(userMapper.userCreateFormToUser(createForm));
    }

    @Override
    public boolean updateUser(final UUID uuid, final UserCreateForm createForm) {
        final User user = getUserByUuid(uuid);
        if (!isLoginAvailable(user.getLogin(), createForm.getLogin())) {
            return false;
        }

        userMapper.fromUserCreateForm(createForm, user);
        userRepository.save(user);
        return true;
    }

    private boolean isLoginAvailable(final String login, final String newLogin) {
        return login.equals(newLogin) || !existsUserByLogin(newLogin);
    }

    @Override
    public void deleteUser(final UUID uuid) {
        userRepository.delete(getUserByUuid(uuid));
    }

    @Override
    public User getUserByUuid(final UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("User with given UUID not found"));
    }

    @Override
    public boolean existsUserByLogin(final String userLogin) {
        return userRepository.existsByLogin(userLogin);
    }
}