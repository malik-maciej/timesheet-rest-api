package com.malik.restapi.service.impl;

import com.malik.restapi.dto.ProjectTableDto;
import com.malik.restapi.exception.NonUniqueException;
import com.malik.restapi.exception.NotFoundException;
import com.malik.restapi.factory.ProjectFactory;
import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.mapper.ProjectMapper;
import com.malik.restapi.model.Project;
import com.malik.restapi.repository.ProjectRepository;
import com.malik.restapi.repository.ProjectViewRepository;
import com.malik.restapi.repository.UserRepository;
import com.malik.restapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ProjectServiceImplTest {

    @Spy
    private ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectViewRepository projectViewRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Captor
    private ArgumentCaptor<Project> projectCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateProject() {
        // given
        final ProjectCreateForm projectCreateForm = ProjectFactory.getProjectCreateForm();
        final Project project = projectMapper.projectCreateFormToProject(projectCreateForm);

        given(projectRepository.save(project)).willReturn(project);

        // when
        projectService.createProject(projectCreateForm);

        // then
        verify(projectRepository).save(projectCaptor.capture());
        then(projectCaptor.getValue().getName()).isEqualTo(projectCreateForm.getName());
        then(projectCaptor.getValue().getUuid()).isNotNull();
    }

    @Test
    void shouldUpdateProjectWhenNewNameIsTheSame() {
        // given
        final Project project = ProjectFactory.getProject();

        final ProjectCreateForm projectCreateForm = ProjectFactory.getProjectCreateForm();
        projectCreateForm.setName(project.getName());

        given(projectRepository.findByUuid(project.getUuid())).willReturn(Optional.of(project));

        // when
        projectService.updateProject(project.getUuid(), projectCreateForm);

        // then
        then(project.getName()).isEqualTo(projectCreateForm.getName());
        then(project.getDescription()).isEqualTo(projectCreateForm.getDescription());
    }

    @Test
    void shouldUpdateProjectWhenNewNameIsDifferentAndAvailable() {
        // given
        final Project project = ProjectFactory.getProject();
        final ProjectCreateForm projectCreateForm = ProjectFactory.getProjectCreateForm();

        given(projectRepository.findByUuid(project.getUuid())).willReturn(Optional.of(project));
        given(projectRepository.existsByName(projectCreateForm.getName())).willReturn(false);

        // when
        projectService.updateProject(project.getUuid(), projectCreateForm);

        // then
        then(project.getName()).isEqualTo(projectCreateForm.getName());
    }

    @Test
    void shouldNotUpdateProjectWhenNewNameIsDifferentAndUnavailable() {
        // given
        final Project project = ProjectFactory.getProject();
        final ProjectCreateForm projectCreateForm = ProjectFactory.getProjectCreateForm();

        given(projectRepository.findByUuid(project.getUuid())).willReturn(Optional.of(project));
        given(projectRepository.existsByName(projectCreateForm.getName())).willReturn(true);

        // when
        final Throwable result = catchThrowable(() -> projectService.updateProject(project.getUuid(), projectCreateForm));

        // then
        then(result)
                .isInstanceOf(NonUniqueException.class)
                .hasMessage("Given name is non unique");
    }

    @Test
    void shouldDeleteProject() {
        // given
        final Project project = ProjectFactory.getProject();
        given(projectRepository.findByUuid(project.getUuid())).willReturn(Optional.of(project));

        // when
        projectService.deleteProject(project.getUuid());

        // then
        verify(projectRepository).delete(project);
    }

    @Test
    void shouldGetProjectByCorrectUuid() {
        // given
        final Project project = ProjectFactory.getProject();
        given(projectRepository.findByUuid(project.getUuid())).willReturn(Optional.of(project));

        // when
        final Project result = projectService.getProjectByUuid(project.getUuid());

        // then
        then(result).isEqualTo(project);
    }

    @Test
    void shouldThrowExceptionWhenProjectWithGivenUuidIsNotInDb() {
        // given
        final UUID uuid = UUID.randomUUID();
        given(projectRepository.findByUuid(uuid)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> projectService.getProjectByUuid(uuid));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Project with given UUID not found");
    }

    @Test
    void shouldThrowExceptionWhenUuidIsNull() {
        // given
        given(projectRepository.findByUuid(null)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> projectService.getProjectByUuid(null));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Project with given UUID not found");
    }

    @Test
    void shouldGetProjectByCorrectName() {
        // given
        final Project project = ProjectFactory.getProject();
        given(projectRepository.findByName(project.getName())).willReturn(Optional.of(project));

        // when
        final Project result = projectService.getProjectByName(project.getName());

        // then
        then(result).isEqualTo(project);
    }

    @Test
    void shouldThrowExceptionWhenProjectWithGivenNameIsNotInDb() {
        // given
        final String name = "random1";
        given(projectRepository.findByName(name)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> projectService.getProjectByName(name));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Project with given name not found");
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // given
        given(projectRepository.findByName(null)).willReturn(Optional.empty());

        // when
        final Throwable result = catchThrowable(() -> projectService.getProjectByName(null));

        // then
        then(result)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Project with given name not found");
    }

    @Test
    void shouldGetAllProjects() {
        // given
        final List<Project> projects = Collections.singletonList(ProjectFactory.getProject());
        given(projectRepository.findAll()).willReturn(projects);

        // when
        final List<ProjectTableDto> result = projectService.getProjects();

        // then
        verify(projectRepository).findAll();
        then(result).hasSize(1);
    }
}