package com.malik.restapi.mapper;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.model.Worktime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorktimeMapper {

    WorktimeDto entityToDto(final Worktime worktime);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "project", ignore = true)
    Worktime formToEntity(final WorktimeCreateForm worktimeCreateForm);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "project", ignore = true)
    void fromWorktimeCreateForm(final WorktimeCreateForm worktimeCreateForm, @MappingTarget final Worktime worktime);
}