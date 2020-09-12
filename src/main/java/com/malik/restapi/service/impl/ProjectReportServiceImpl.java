package com.malik.restapi.service.impl;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.dto.UserForProjectReportDto;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.service.ProjectReportService;
import com.malik.restapi.service.ProjectService;
import com.malik.restapi.util.TimePeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.malik.restapi.util.ProjectUtil.getProjectTotalCost;
import static com.malik.restapi.util.ProjectUtil.getProjectTotalWorktime;
import static com.malik.restapi.util.UserUtil.getUserCostInProject;
import static com.malik.restapi.util.UserUtil.getUserTimeInProject;
import static com.malik.restapi.util.Util.getRoundedSum;

@Service
class ProjectReportServiceImpl implements ProjectReportService {

    private final ProjectService projectService;

    ProjectReportServiceImpl(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectReportDto getReport(final UUID uuid, final TimePeriod timePeriod) {
        final Project project = projectService.getProjectByUuid(uuid);
        final BigDecimal projectTotalCost = getRoundedSum(getProjectTotalCost(timePeriod, project));
        final double projectTotalWorktime = getProjectTotalWorktime(timePeriod, project);
        final boolean budgetExceeded = project.getBudget().compareTo(projectTotalCost) < 0;
        final List<UserForProjectReportDto> usersReports = getUsersReports(timePeriod, project);

        return new ProjectReportDto(project.getUuid(), project.getName(), projectTotalCost,
                getRoundedSum(projectTotalWorktime), budgetExceeded, usersReports);
    }

    private List<UserForProjectReportDto> getUsersReports(final TimePeriod timePeriod, final Project project) {
        return project.getUsers().stream()
                .map(user -> getUserForProjectReportDto(timePeriod, project, user))
                .collect(Collectors.toList());
    }

    private UserForProjectReportDto getUserForProjectReportDto(final TimePeriod timePeriod, final Project project, final User user) {
        final double timeInProject = getUserTimeInProject(project, user, timePeriod);
        final BigDecimal costInProject = getRoundedSum(getUserCostInProject(user, timeInProject));
        return new UserForProjectReportDto(user.getUuid(), user.getLogin(), costInProject, getRoundedSum(timeInProject));
    }
}