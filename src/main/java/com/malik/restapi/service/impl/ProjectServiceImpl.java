package com.malik.restapi.service.impl;

import com.malik.restapi.dto.ProjectDto;
import com.malik.restapi.dto.ProjectTableDto;
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
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.malik.restapi.util.ProjectUtil.getBudgetPercentage;

@Service
class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    private final ProjectRepository projectRepository;
    private final ProjectViewRepository projectViewRepository;
    private final UserService userService;

    ProjectServiceImpl(ProjectRepository projectRepository, ProjectViewRepository projectViewRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.projectViewRepository = projectViewRepository;
        this.userService = userService;
    }

    @Override
    public Page<ProjectDto> getFilteredProjects(ProjectFilterForm filterForm, Pageable pageable) {
        ProjectSpecification specification = new ProjectSpecification(filterForm);
        return projectViewRepository.findAll(specification, pageable)
                .map(projectMapper::projectFilterViewToProjectDto);
    }

    @Override
    public Project createProject(ProjectCreateForm createForm) {
        return projectRepository.save(projectMapper.projectCreateFormToProject(createForm));
    }

    @Override
    public boolean updateProject(UUID uuid, ProjectCreateForm createForm) {
        Project project = getProjectByUuid(uuid);
        if (!isProjectNameAvailable(project.getName(), createForm.getName())) {
            return false;
        }

        projectMapper.fromProjectCreateForm(createForm, project);
        projectRepository.save(project);
        return true;
    }

    private boolean isProjectNameAvailable(String name, String newName) {
        return name.equals(newName) || !projectRepository.existsByName(newName);
    }

    @Override
    public void deleteProject(UUID uuid) {
        projectRepository.delete(getProjectByUuid(uuid));
    }

    @Override
    public void addUserToProject(UUID projectUuid, UUID userUuid) {
        Project project = getProjectByUuid(projectUuid);
        User user = userService.getUserByUuid(userUuid);

        project.addUser(user);
        projectRepository.save(project);
    }

    @Override
    public void deleteUserFromProject(UUID projectUuid, UUID userUuid) {
        Project project = getProjectByUuid(projectUuid);
        User user = userService.getUserByUuid(userUuid);

        project.removeUser(user);
        projectRepository.save(project);
    }

    @Override
    public Project getProjectByUuid(UUID uuid) {
        return projectRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Project with given UUID not found"));
    }

    @Override
    public Project getProjectByName(String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Project with given name not found"));
    }

    @Override
    public boolean existProjectByName(String name) {
        return projectRepository.existsByName(name);
    }

    @Override
    public List<ProjectTableDto> getProjects() {
        return projectRepository.findAll().stream()
                .map(this::getProjectTableDto)
                .collect(Collectors.toList());
    }

    private ProjectTableDto getProjectTableDto(Project project) {
        List<String> membersLogins = project.getUsers().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
        return new ProjectTableDto(project.getUuid(), project.getName(), membersLogins, getBudgetPercentage(project));
    }
}