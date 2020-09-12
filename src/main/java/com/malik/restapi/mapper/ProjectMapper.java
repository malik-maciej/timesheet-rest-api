package com.malik.restapi.mapper;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.ProjectView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto projectToProjectDto(final Project project);

    ProjectDto projectFilterViewToProjectDto(final ProjectView projectView);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    Project projectCreateFormToProject(final ProjectCreateForm projectCreateForm);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    void fromProjectCreateForm(final ProjectCreateForm projectCreateForm, @MappingTarget final Project project);
}