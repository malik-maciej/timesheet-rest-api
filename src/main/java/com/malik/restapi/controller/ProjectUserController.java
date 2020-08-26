package com.malik.restapi.controller;

import com.malik.restapi.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
class ProjectUserController {

    private final ProjectService projectService;

    ProjectUserController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PutMapping("/{projectUuid}/users/{userUuid}")
    @ResponseStatus(HttpStatus.OK)
    void addUserToProject(@PathVariable UUID projectUuid, @PathVariable UUID userUuid) {
        projectService.addUserToProject(projectUuid, userUuid);
    }

    @DeleteMapping("/{projectUuid}/users/{userUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserFromProject(@PathVariable UUID projectUuid, @PathVariable UUID userUuid) {
        projectService.deleteUserFromProject(projectUuid, userUuid);
    }
}