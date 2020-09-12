package com.malik.restapi.controller;

import com.malik.restapi.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class ProjectUserController {

    interface Routes {
        String ROOT = "/projects/{projectUuid}/users/{userUuid}";
    }

    private final ProjectService projectService;

    ProjectUserController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PutMapping(Routes.ROOT)
    @ResponseStatus(HttpStatus.OK)
    void addUserToProject(@PathVariable final UUID projectUuid, @PathVariable final UUID userUuid) {
        projectService.addUserToProject(projectUuid, userUuid);
    }

    @DeleteMapping(Routes.ROOT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserFromProject(@PathVariable final UUID projectUuid, @PathVariable final UUID userUuid) {
        projectService.deleteUserFromProject(projectUuid, userUuid);
    }
}