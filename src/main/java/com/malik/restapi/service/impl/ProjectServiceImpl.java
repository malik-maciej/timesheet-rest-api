package com.malik.restapi.service.impl;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.exception.NonUniqueException;
import com.malik.restapi.exception.NotFoundException;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.form.ProjectFilterForm;
import com.malik.restapi.mapper.ProjectMapper;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.repository.ProjectRepository;
import com.malik.restapi.repository.ProjectViewRepository;
import com.malik.restapi.service.ProjectService;
import com.malik.restapi.service.UserService;
import com.malik.restapi.specification.ProjectSpecification;
import com.malik.restapi.util.ProjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectViewRepository projectViewRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;

    ProjectServiceImpl(final ProjectRepository projectRepository, final ProjectViewRepository projectViewRepository,
                       final ProjectMapper projectMapper, final UserService userService) {
        this.projectRepository = projectRepository;
        this.projectViewRepository = projectViewRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
    }

    @Override
    public Page<ProjectDto> getFilteredProjects(final ProjectFilterForm filterForm, final Pageable pageable) {
        final ProjectSpecification specification = new ProjectSpecification(filterForm);
        return projectViewRepository.findAll(specification, pageable)
                .map(projectMapper::viewToDto);
    }

    @Override
    public ProjectDto createProject(final ProjectCreateForm createForm) {
        if (projectRepository.existsByName(createForm.getName())) {
            throw new NonUniqueException(ErrorMessages.PROJECT_NAME_ALREADY_EXISTS);
        }

        final Project project = projectRepository.save(projectMapper.formToEntity(createForm));
        return projectMapper.entityToDto(project);
    }

    @Override
    public void updateProject(final UUID uuid, final ProjectCreateForm createForm) {
        final Project project = getProjectByUuid(uuid);
        if (isFalse(isProjectNameAvailable(project.getName(), createForm.getName()))) {
            throw new NonUniqueException(ErrorMessages.PROJECT_NAME_ALREADY_EXISTS);
        }

        projectMapper.fromProjectCreateForm(createForm, project);
        projectRepository.save(project);
    }

    private boolean isProjectNameAvailable(final String name, final String newName) {
        return name.equals(newName) || isFalse(projectRepository.existsByName(newName));
    }

    @Override
    public void deleteProject(final UUID uuid) {
        projectRepository.delete(getProjectByUuid(uuid));
    }

    @Override
    public void addUserToProject(final UUID projectUuid, final UUID userUuid) {
        final Project project = getProjectByUuid(projectUuid);
        final User user = userService.getUserByUuid(userUuid);

        project.addUser(user);
        projectRepository.save(project);
    }

    @Override
    public void deleteUserFromProject(final UUID projectUuid, final UUID userUuid) {
        final Project project = getProjectByUuid(projectUuid);
        final User user = userService.getUserByUuid(userUuid);

        project.removeUser(user);
        projectRepository.save(project);
    }

    @Override
    public Project getProjectByUuid(final UUID uuid) {
        return projectRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.PROJECT_UUID_NOT_FOUND));
    }

    @Override
    public Project getProjectByName(final String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.PROJECT_NAME_NOT_FOUND));
    }

    @Override
    public List<ProjectTableDto> getProjects() {
        return projectRepository.findAll().stream()
                .map(this::getProjectTableDto)
                .collect(Collectors.toList());
    }

    private ProjectTableDto getProjectTableDto(final Project project) {
        final List<String> membersLogins = project.getUsers().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
        return ProjectTableDto.of(project.getUuid(), project.getName(), membersLogins, ProjectUtil.getBudgetPercentage(project));
    }

    static class ErrorMessages {
        static final String PROJECT_UUID_NOT_FOUND = "Project with given UUID not found";
        static final String PROJECT_NAME_NOT_FOUND = "Project with given name not found";
        static final String PROJECT_NAME_ALREADY_EXISTS = "Project with given name is already exists";
    }
}