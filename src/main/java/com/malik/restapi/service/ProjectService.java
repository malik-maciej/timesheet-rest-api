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

    Page<ProjectDto> getFilteredProjects(final ProjectFilterForm filterForm, final Pageable pageable);

    Project createProject(final ProjectCreateForm createForm);

    boolean updateProject(final UUID uuid, final ProjectCreateForm createForm);

    void deleteProject(final UUID uuid);

    void addUserToProject(final UUID projectUuid, final UUID userUuid);

    void deleteUserFromProject(final UUID projectUuid, final UUID userUuid);

    Project getProjectByUuid(final UUID uuid);

    Project getProjectByName(final String name);

    boolean existProjectByName(final String name);

    List<ProjectTableDto> getProjects();
}