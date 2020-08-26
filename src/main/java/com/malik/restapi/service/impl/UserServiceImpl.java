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
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getFilteredUsers(UserFilterForm userFilterForm, Pageable pageable) {
        UserSpecification specification = new UserSpecification(userFilterForm);
        return userRepository.findAll(specification, pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public User createUser(UserCreateForm createForm) {
        return userRepository.save(userMapper.userCreateFormToUser(createForm));
    }

    @Override
    public boolean updateUser(UUID uuid, UserCreateForm createForm) {
        User user = getUserByUuid(uuid);
        if (!isLoginAvailable(user.getLogin(), createForm.getLogin())) {
            return false;
        }

        userMapper.fromUserCreateForm(createForm, user);
        userRepository.save(user);
        return true;
    }

    private boolean isLoginAvailable(String login, String newLogin) {
        return login.equals(newLogin) || !existsUserByLogin(newLogin);
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepository.delete(getUserByUuid(uuid));
    }

    @Override
    public User getUserByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("User with given UUID not found"));
    }

    @Override
    public boolean existsUserByLogin(String userLogin) {
        return userRepository.existsByLogin(userLogin);
    }
}