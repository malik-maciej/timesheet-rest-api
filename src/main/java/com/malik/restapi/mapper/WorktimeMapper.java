package com.malik.restapi.mapper;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.model.Worktime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface WorktimeMapper {

    WorktimeDto worktimeToWorktimeDto(Worktime worktime);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "project", ignore = true)
    Worktime worktimeCreateFormToWorktime(WorktimeCreateForm worktimeCreateForm);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "project", ignore = true)
    void fromWorktimeCreateForm(WorktimeCreateForm worktimeCreateForm, @MappingTarget Worktime worktime);
}