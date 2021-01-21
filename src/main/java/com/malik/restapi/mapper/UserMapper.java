package com.malik.restapi.mapper;

import com.malik.restapi.dto.UserDto;
import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto entityToDto(final User user);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    User formToEntity(final UserCreateForm userCreateForm);

    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    void fromUserCreateForm(final UserCreateForm userCreateForm, @MappingTarget final User user);
}