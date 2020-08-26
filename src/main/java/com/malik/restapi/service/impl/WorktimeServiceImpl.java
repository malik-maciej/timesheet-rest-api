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
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class WorktimeServiceImpl implements WorktimeService {

    private final WorktimeMapper worktimeMapper = Mappers.getMapper(WorktimeMapper.class);

    private final WorktimeRepository worktimeRepository;
    private final UserService userService;
    private final ProjectService projectService;

    WorktimeServiceImpl(WorktimeRepository worktimeRepository, UserService userService, ProjectService projectService) {
        this.worktimeRepository = worktimeRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public List<WorktimeDto> getAllWorktimes(UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        return worktimeRepository.findAllByUser(user).stream()
                .map(worktimeMapper::worktimeToWorktimeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Worktime createWorktime(UUID uuid, WorktimeCreateForm createForm) {
        Project project = projectService.getProjectByName(createForm.getProjectName());
        Worktime worktime = worktimeMapper.worktimeCreateFormToWorktime(createForm);
        User user = userService.getUserByUuid(uuid);
        user.addWorktime(worktime);
        project.addWorktime(worktime);
        return worktimeRepository.save(worktime);
    }

    @Override
    public void updateWorktime(UUID uuid, WorktimeCreateForm createForm) {
        Worktime worktime = getWorktimeByUuid(uuid);
        worktimeMapper.fromWorktimeCreateForm(createForm, worktime);
        worktimeRepository.save(worktime);
    }

    @Override
    public void deleteWorktime(UUID uuid) {
        worktimeRepository.delete(getWorktimeByUuid(uuid));
    }

    private Worktime getWorktimeByUuid(UUID uuid) {
        return worktimeRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Worktime with given UUID not found"));
    }
}