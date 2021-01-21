package com.malik.restapi.controller;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.mapper.WorktimeMapper;
import com.malik.restapi.service.WorktimeService;
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
class WorktimeController {

    private final WorktimeService worktimeService;
    private final WorktimeMapper worktimeMapper;

    WorktimeController(final WorktimeService worktimeService, final WorktimeMapper worktimeMapper) {
        this.worktimeService = worktimeService;
        this.worktimeMapper = worktimeMapper;
    }

    @GetMapping(Routes.ROOT)
    List<WorktimeDto> getWorktimes(@PathVariable final UUID userUuid) {
        return worktimeService.getAllWorktimes(userUuid);
    }

    @PostMapping(Routes.ROOT)
    @ResponseStatus(HttpStatus.CREATED)
    WorktimeDto createWorktime(@PathVariable final UUID userUuid,
                               @RequestBody @Valid final WorktimeCreateForm createForm) {
        return worktimeMapper.entityToDto(worktimeService.createWorktime(userUuid, createForm));
    }

    @PutMapping(Routes.WORKTIME_BY_UUID)
    void updateUser(@PathVariable final UUID worktimeUuid,
                    @RequestBody @Valid final WorktimeCreateForm createForm) {
        worktimeService.updateWorktime(worktimeUuid, createForm);
    }

    @DeleteMapping(Routes.WORKTIME_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable final UUID worktimeUuid) {
        worktimeService.deleteWorktime(worktimeUuid);
    }

    private static class Routes {
        static final String ROOT = "/users/{userUuid}/worktimes";
        static final String WORKTIME_BY_UUID = ROOT + "/{worktimeUuid}";
    }
}