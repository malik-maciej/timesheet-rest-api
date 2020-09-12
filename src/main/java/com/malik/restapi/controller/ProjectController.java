package com.malik.restapi.controller;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.mapper.ProjectMapper;
import com.malik.restapi.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
class ProjectController {

    interface Routes {
        String ROOT = "/projects";
        String PROJECT_BY_UUID = ROOT + "/{uuid}";
        String FILTER = ROOT + "/filter";
    }

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    ProjectController(final ProjectService projectService, final ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping(Routes.ROOT)
    @ResponseStatus(value = HttpStatus.OK)
    List<ProjectTableDto> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping(Routes.FILTER)
    @ResponseStatus(value = HttpStatus.OK)
    Page<ProjectDto> getFilteredProjects(@RequestBody @Valid final ProjectFilterForm filterForm, final Pageable pageable) {
        return projectService.getFilteredProjects(filterForm, pageable);
    }

    @PostMapping(Routes.ROOT)
    ResponseEntity<?> createProject(@RequestBody @Valid final ProjectCreateForm createForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors() || projectService.existProjectByName(createForm.getName())) {
            return ResponseEntity.badRequest().build();
        }

        final ProjectDto result = projectMapper.projectToProjectDto(projectService.createProject(createForm));
        return ResponseEntity.created(URI.create("/" + result.getName())).body(result);
    }

    @PutMapping(Routes.PROJECT_BY_UUID)
    ResponseEntity<?> updateProject(@PathVariable final UUID uuid, @RequestBody @Valid final ProjectCreateForm createForm, final BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Complete all fields properly");
        }

        if (projectService.updateProject(uuid, createForm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(Routes.PROJECT_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable final UUID uuid) {
        projectService.deleteProject(uuid);
    }
}