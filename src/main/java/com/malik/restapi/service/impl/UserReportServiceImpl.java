package com.malik.restapi.service.impl;

import com.malik.restapi.dto.UserProjectReportDto;
import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.service.UserReportService;
import com.malik.restapi.service.UserService;
import com.malik.restapi.util.TimePeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.malik.restapi.util.UserUtil.getUserCostInProject;
import static com.malik.restapi.util.UserUtil.getUserTimeInProject;
import static com.malik.restapi.util.UserUtil.getUserTotalCost;
import static com.malik.restapi.util.Util.getRoundedSum;

@Service
class UserReportServiceImpl implements UserReportService {

    private final UserService userService;

    UserReportServiceImpl(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserReportDto getReport(final UUID uuid, final TimePeriod timePeriod) {
        final User user = userService.getUserByUuid(uuid);
        final BigDecimal userTotalCost = getRoundedSum(getUserTotalCost(timePeriod, user));
        final List<UserProjectReportDto> projectsReports = getProjectsReports(user, timePeriod);
        return new UserReportDto(user.getUuid(), user.getLogin(), userTotalCost, projectsReports);
    }

    private List<UserProjectReportDto> getProjectsReports(final User user, final TimePeriod timePeriod) {
        return user.getProjects().stream()
                .map(project -> getUserProjectReportDto(user, timePeriod, project))
                .collect(Collectors.toList());
    }

    private UserProjectReportDto getUserProjectReportDto(final User user, final TimePeriod timePeriod, final Project project) {
        final double timeInProject = getUserTimeInProject(project, user, timePeriod);
        final BigDecimal costInProject = getRoundedSum(getUserCostInProject(user, timeInProject));
        return new UserProjectReportDto(project.getUuid(), project.getName(), costInProject, getRoundedSum(timeInProject));
    }
}