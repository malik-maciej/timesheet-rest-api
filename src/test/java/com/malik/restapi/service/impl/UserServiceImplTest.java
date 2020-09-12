package com.malik.restapi.service.impl;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.exception.NotFoundException;
import com.malik.restapi.factory.UserFactory;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.form.UserFilterForm;
import com.malik.restapi.mapper.UserMapper;
import com.malik.restapi.model.User;
import com.malik.restapi.repository.UserRepository;
import com.malik.restapi.specification.UserSpecification;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetAllUsers() {
        // given
        final List<User> users = Collections.singletonList(UserFactory.getUser());
        given(userRepository.findAll()).willReturn(users);

        // when
        final List<UserDto> result = userService.getAllUsers();

        // then
        verify(userRepository).findAll();
        then(result).hasSize(1);
    }

    @Test
    void shouldGetFilteredUsers() {
        // given
        final List<User> users = Collections.singletonList(UserFactory.getUser());
        final UserFilterForm userFilterForm = new UserFilterForm();
        final Pageable pageable = PageRequest.of(0, 20);

        given(userRepository.findAll(any(UserSpecification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(users, pageable, users.size()));

        // when
        final Page<UserDto> result = userService.getFilteredUsers(userFilterForm, pageable);

        // then
        verify(userRepository).findAll(any(UserSpecification.class), any(Pageable.class));
        then(result.getTotalElements()).isOne();
    }

    @Test
    void shouldCreateUser() {
        // given
        final UserCreateForm userCreateForm = UserFactory.getUserCreateForm();
        final User user = userMapper.userCreateFormToUser(userCreateForm);

        given(userRepository.save(user)).willReturn(user);

        // when
        userService.createUser(userCreateForm);

        // then
        verify(userRepository).save(userCaptor.capture());
        then(userCaptor.getValue().getLogin()).isEqualTo(userCreateForm.getLogin());
        then(userCaptor.getValue().getUuid()).isNotNull();
    }

    @Test
    void shouldUpdateUserWhenNewLoginIsTheSame() {
        // given
        final User user = UserFactory.getUser();

        final UserCreateForm userCreateForm = UserFactory.getUserCreateForm();
        userCreateForm.setLogin(user.getLogin());

        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));

        // when
        final boolean result = userService.updateUser(user.getUuid(), userCreateForm);

        // then
        then(result).isTrue();
        then(user.getLogin()).isEqualTo(userCreateForm.getLogin());
        then(user.getSurname()).isEqualTo(userCreateForm.getSurname());
    }

    @Test
    void shouldUpdateUserWhenNewLoginIsDifferentAndAvailable() {
        // given
        final User user = UserFactory.getUser();
        final UserCreateForm userCreateForm = UserFactory.getUserCreateForm();

        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));
        given(userRepository.existsByLogin(userCreateForm.getLogin())).willReturn(false);

        // when
        final boolean result = userService.updateUser(user.getUuid(), userCreateForm);

        // then
        then(result).isTrue();
        then(user.getLogin()).isEqualTo(userCreateForm.getLogin());
    }

    @Test
    void shouldNotUpdateUserWhenNewLoginIsDifferentAndUnavailable() {
        // given
        final User user = UserFactory.getUser();
        final UserCreateForm userCreateForm = UserFactory.getUserCreateForm();

        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));
        given(userRepository.existsByLogin(userCreateForm.getLogin())).willReturn(true);

        // when
        final boolean result = userService.updateUser(user.getUuid(), userCreateForm);

        // then
        then(result).isFalse();
    }

    @Test
    void shouldDeleteUser() {
        // given
        final User user = UserFactory.getUser();
        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));

        // when
        userService.deleteUser(user.getUuid());

        // then
        verify(userRepository).delete(user);
    }

    @Test
    void shouldGetUserByCorrectUuid() {
        // given
        final User user = UserFactory.getUser();
        given(userRepository.findByUuid(user.getUuid())).willReturn(Optional.of(user));

        // when
        final User result = userService.getUserByUuid(user.getUuid());

        // then
        then(result).isEqualTo(user);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotInDb() {
        // given
        final UUID uuid = UUID.randomUUID();
        given(userRepository.findByUuid(uuid)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> userService.getUserByUuid(uuid));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with given UUID not found");
    }

    @Test
    void shouldThrowExceptionWhenUuidIsNull() {
        // given
        given(userRepository.findByUuid(null)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> userService.getUserByUuid(null));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with given UUID not found");
    }

    @Test
    void shouldExistsByLoginReturnTrue() {
        // given
        final String login = RandomString.make();
        given(userRepository.existsByLogin(login)).willReturn(true);

        // when
        final boolean result = userService.existsUserByLogin(login);

        // then
        then(result).isTrue();
    }
}