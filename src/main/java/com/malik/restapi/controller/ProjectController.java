package com.malik.restapi.controller;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
class ProjectController {

    private final ProjectService projectService;

    ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(Routes.ROOT)
    List<ProjectTableDto> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping(Routes.FILTER)
    Page<ProjectDto> getFilteredProjects(@RequestBody @Valid final ProjectFilterForm filterForm, final Pageable pageable) {
        return projectService.getFilteredProjects(filterForm, pageable);
    }

    @PostMapping(Routes.ROOT)
    @ResponseStatus(HttpStatus.CREATED)
    ProjectDto createProject(@RequestBody @Valid final ProjectCreateForm createForm) {
        return projectService.createProject(createForm);
    }

    @PutMapping(Routes.PROJECT_BY_UUID)
    @ResponseStatus(HttpStatus.OK)
    void updateProject(@PathVariable final UUID uuid, @RequestBody @Valid final ProjectCreateForm createForm) {
        projectService.updateProject(uuid, createForm);
    }

    @DeleteMapping(Routes.PROJECT_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable final UUID uuid) {
        projectService.deleteProject(uuid);
    }

    private static class Routes {
        static final String ROOT = "/projects";
        static final String PROJECT_BY_UUID = ROOT + "/{uuid}";
        static final String FILTER = ROOT + "/filter";
    }
}