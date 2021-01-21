package com.malik.restapi.service.impl;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.dto.UserForProjectReportDto;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.service.ProjectReportService;
import com.malik.restapi.service.ProjectService;
import com.malik.restapi.util.ProjectUtil;
import com.malik.restapi.util.TimePeriod;
import com.malik.restapi.util.UserUtil;
import com.malik.restapi.util.Util;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class ProjectReportServiceImpl implements ProjectReportService {

    private final ProjectService projectService;

    ProjectReportServiceImpl(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectReportDto getReport(final UUID uuid, final TimePeriod timePeriod) {
        final Project project = projectService.getProjectByUuid(uuid);
        final BigDecimal projectTotalCost = Util.getRoundedSum(ProjectUtil.getProjectTotalCost(timePeriod, project));
        final double projectTotalWorktime = ProjectUtil.getProjectTotalWorktime(timePeriod, project);
        final boolean budgetExceeded = project.getBudget().compareTo(projectTotalCost) < 0;
        final List<UserForProjectReportDto> usersReports = getUsersReports(timePeriod, project);

        return ProjectReportDto.of(project.getUuid(), project.getName(), projectTotalCost,
                Util.getRoundedSum(projectTotalWorktime), budgetExceeded, usersReports);
    }

    private List<UserForProjectReportDto> getUsersReports(final TimePeriod timePeriod, final Project project) {
        return project.getUsers().stream()
                .map(user -> getUserForProjectReportDto(timePeriod, project, user))
                .collect(Collectors.toList());
    }

    private UserForProjectReportDto getUserForProjectReportDto(final TimePeriod timePeriod, final Project project, final User user) {
        final double timeInProject = UserUtil.getUserTimeInProject(project, user, timePeriod);
        final BigDecimal costInProject = Util.getRoundedSum(UserUtil.getUserCostInProject(user, timeInProject));
        return UserForProjectReportDto.of(user.getUuid(), user.getLogin(), costInProject, Util.getRoundedSum(timeInProject));
    }
}