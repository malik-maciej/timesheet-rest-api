package com.malik.restapi.mapper;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.ProjectView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProjectMapper {

    ProjectDto projectToProjectDto(Project project);

    ProjectDto projectFilterViewToProjectDto(ProjectView projectView);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    Project projectCreateFormToProject(ProjectCreateForm projectCreateForm);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "worktimes", ignore = true)
    void fromProjectCreateForm(ProjectCreateForm projectCreateForm, @MappingTarget Project project);
}