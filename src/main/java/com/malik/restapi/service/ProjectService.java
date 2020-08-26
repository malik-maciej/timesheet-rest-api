package com.malik.restapi.service;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProjectService {

    Page<ProjectDto> getFilteredProjects(ProjectFilterForm filterForm, Pageable pageable);

    Project createProject(ProjectCreateForm createForm);

    boolean updateProject(UUID uuid, ProjectCreateForm createForm);

    void deleteProject(UUID uuid);

    void addUserToProject(UUID projectUuid, UUID userUuid);

    void deleteUserFromProject(UUID projectUuid, UUID userUuid);

    Project getProjectByUuid(UUID uuid);

    Project getProjectByName(String name);

    boolean existProjectByName(String name);

    List<ProjectTableDto> getProjects();
}