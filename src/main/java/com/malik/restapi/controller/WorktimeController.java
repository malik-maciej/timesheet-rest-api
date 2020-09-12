package com.malik.restapi.controller;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.mapper.WorktimeMapper;
import com.malik.restapi.service.WorktimeService;
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
class WorktimeController {

    interface Routes {
        String ROOT = "/users/{userUuid}/worktimes";
        String WORKTIME_BY_UUID = ROOT + "/{worktimeUuid}";
    }

    private final WorktimeService worktimeService;
    private final WorktimeMapper worktimeMapper;

    WorktimeController(final WorktimeService worktimeService, final WorktimeMapper worktimeMapper) {
        this.worktimeService = worktimeService;
        this.worktimeMapper = worktimeMapper;
    }

    @GetMapping(Routes.ROOT)
    @ResponseStatus(value = HttpStatus.OK)
    List<WorktimeDto> getWorktimes(@PathVariable final UUID userUuid) {
        return worktimeService.getAllWorktimes(userUuid);
    }

    @PostMapping(Routes.ROOT)
    ResponseEntity<?> createWorktime(@PathVariable final UUID userUuid, @RequestBody @Valid final WorktimeCreateForm createForm,
                                     final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        final WorktimeDto result = worktimeMapper.worktimeToWorktimeDto(worktimeService.createWorktime(userUuid, createForm));
        return ResponseEntity.created(URI.create("/" + result.getUuid())).body(result);
    }

    @PutMapping(Routes.WORKTIME_BY_UUID)
    ResponseEntity<Void> updateUser(@PathVariable final UUID worktimeUuid, @RequestBody @Valid final WorktimeCreateForm createForm,
                                    final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        worktimeService.updateWorktime(worktimeUuid, createForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(Routes.WORKTIME_BY_UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable final UUID worktimeUuid) {
        worktimeService.deleteWorktime(worktimeUuid);
    }
}