package com.malik.restapi.service.impl;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.exception.NotFoundException;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.mapper.WorktimeMapper;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.model.Worktime;
import com.malik.restapi.repository.WorktimeRepository;
import com.malik.restapi.service.ProjectService;
import com.malik.restapi.service.UserService;
import com.malik.restapi.service.WorktimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class WorktimeServiceImpl implements WorktimeService {

    private final WorktimeRepository worktimeRepository;
    private final WorktimeMapper worktimeMapper;
    private final UserService userService;
    private final ProjectService projectService;

    WorktimeServiceImpl(final WorktimeRepository worktimeRepository, final WorktimeMapper worktimeMapper,
                        final UserService userService, final ProjectService projectService) {
        this.worktimeRepository = worktimeRepository;
        this.worktimeMapper = worktimeMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public List<WorktimeDto> getAllWorktimes(final UUID uuid) {
        final User user = userService.getUserByUuid(uuid);
        return worktimeRepository.findAllByUser(user).stream()
                .map(worktimeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Worktime createWorktime(final UUID uuid, final WorktimeCreateForm createForm) {
        final Project project = projectService.getProjectByName(createForm.getProjectName());
        final Worktime worktime = worktimeMapper.formToEntity(createForm);
        final User user = userService.getUserByUuid(uuid);
        user.addWorktime(worktime);
        project.addWorktime(worktime);
        return worktimeRepository.save(worktime);
    }

    @Override
    public void updateWorktime(final UUID uuid, final WorktimeCreateForm createForm) {
        final Worktime worktime = getWorktimeByUuid(uuid);
        worktimeMapper.fromWorktimeCreateForm(createForm, worktime);
        worktimeRepository.save(worktime);
    }

    @Override
    public void deleteWorktime(final UUID uuid) {
        worktimeRepository.delete(getWorktimeByUuid(uuid));
    }

    private Worktime getWorktimeByUuid(final UUID uuid) {
        return worktimeRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.WORKTIME_NOT_FOUND));
    }

    private static class ErrorMessages {
        static final String WORKTIME_NOT_FOUND = "Worktime with given UUID not found";
    }
}