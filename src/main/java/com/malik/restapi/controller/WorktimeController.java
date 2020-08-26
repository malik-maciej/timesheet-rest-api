package com.malik.restapi.controller;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.mapper.WorktimeMapper;
import com.malik.restapi.service.WorktimeService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
class WorktimeController {

    private final WorktimeMapper worktimeMapper = Mappers.getMapper(WorktimeMapper.class);

    private final WorktimeService worktimeService;

    WorktimeController(WorktimeService worktimeService) {
        this.worktimeService = worktimeService;
    }

    @GetMapping("/{userUuid}/worktimes")
    @ResponseStatus(value = HttpStatus.OK)
    List<WorktimeDto> getWorktimes(@PathVariable UUID userUuid) {
        return worktimeService.getAllWorktimes(userUuid);
    }

    @PostMapping("/{userUuid}/worktimes")
    ResponseEntity<?> createWorktime(@PathVariable UUID userUuid, @RequestBody @Valid WorktimeCreateForm createForm,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        WorktimeDto result = worktimeMapper.worktimeToWorktimeDto(worktimeService.createWorktime(userUuid, createForm));
        return ResponseEntity.created(URI.create("/" + result.getUuid())).body(result);
    }

    @PutMapping("/{userUuid}/worktimes/{worktimeUuid}")
    ResponseEntity<Void> updateUser(@PathVariable UUID worktimeUuid, @RequestBody @Valid WorktimeCreateForm createForm,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        worktimeService.updateWorktime(worktimeUuid, createForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userUuid}/worktimes/{worktimeUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProject(@PathVariable UUID worktimeUuid) {
        worktimeService.deleteWorktime(worktimeUuid);
    }
}