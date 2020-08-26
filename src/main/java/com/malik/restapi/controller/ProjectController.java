package com.malik.restapi.controller;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.mapper.ProjectMapper;
import com.malik.restapi.service.ProjectService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
class ProjectController {

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    private final ProjectService projectService;

    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    List<ProjectTableDto> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/filter")
    @ResponseStatus(value = HttpStatus.OK)
    Page<ProjectDto> getFilteredProjects(@RequestBody @Valid ProjectFilterForm filterForm, Pageable pageable) {
        return projectService.getFilteredProjects(filterForm, pageable);
    }

    @PostMapping
    ResponseEntity<?> createProject(@RequestBody @Valid ProjectCreateForm createForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || projectService.existProjectByName(createForm.getName())) {
            return ResponseEntity.badRequest().build();
        }

        ProjectDto result = projectMapper.projectToProjectDto(projectService.createProject(createForm));
        return ResponseEntity.created(URI.create("/" + result.getName())).body(result);
    }

    @PutMapping("/{uuid}")
    ResponseEntity<?> updateProject(@PathVariable UUID uuid, @RequestBody @Valid ProjectCreateForm createForm, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Complete all fields properly");
        }

        if (projectService.updateProject(uuid, createForm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable UUID uuid) {
        projectService.deleteProject(uuid);
    }
}