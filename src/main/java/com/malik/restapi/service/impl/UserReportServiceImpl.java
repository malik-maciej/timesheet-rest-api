package com.malik.restapi.service.impl;

import com.malik.restapi.dto.UserProjectReportDto;
import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.model.User;
import com.malik.restapi.service.UserReportService;
import com.malik.restapi.service.UserService;
import com.malik.restapi.timeperiod.TimePeriod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.malik.restapi.util.UserUtil.*;
import static com.malik.restapi.util.Util.getRoundedSum;

@Service
class UserReportServiceImpl implements UserReportService {

    private final UserService userService;

    UserReportServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserReportDto getReport(UUID uuid, TimePeriod timePeriod) {
        User user = userService.getUserByUuid(uuid);
        BigDecimal userTotalCost = getRoundedSum(getUserTotalCost(timePeriod, user));
        List<UserProjectReportDto> projectsReports = getProjectsReports(user, timePeriod);
        return new UserReportDto(user.getUuid(), user.getLogin(), userTotalCost, projectsReports);
    }

    private List<UserProjectReportDto> getProjectsReports(User user, TimePeriod timePeriod) {
        return user.getProjects().stream()
                .map(project -> {
                    double timeInProject = getUserTimeInProject(project, user, timePeriod);
                    BigDecimal costInProject = getRoundedSum(getUserCostInProject(user, timeInProject));
                    return new UserProjectReportDto(project.getUuid(), project.getName(), costInProject,
                            getRoundedSum(timeInProject));
                })
                .collect(Collectors.toList());
    }
}