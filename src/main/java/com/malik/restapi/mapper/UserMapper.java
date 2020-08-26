package com.malik.restapi.mapper;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    UserDto userToUserDto(User user);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    User userCreateFormToUser(UserCreateForm userCreateForm);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    void fromUserCreateForm(UserCreateForm userCreateForm, @MappingTarget User user);
}