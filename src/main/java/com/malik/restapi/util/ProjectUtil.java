package com.malik.restapi.util;

import com.malik.restapi.model.Project;
import com.malik.restapi.timeperiod.TimePeriod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public final class ProjectUtil {

    private ProjectUtil() {
    }

    public static BigDecimal getBudgetPercentage(Project project) {
        return getCostOfProject(project)
                .multiply(BigDecimal.valueOf(100))
                .divide(project.getBudget(), 3, RoundingMode.CEILING);
    }

    private static BigDecimal getCostOfProject(Project project) {
        return project.getWorktimes().stream()
                .map(worktime -> {
                    BigDecimal userSalaryPerHour = worktime.getUser().getSalaryPerHour();
                    double workTimeInSeconds = Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds();
                    return userSalaryPerHour.multiply(BigDecimal.valueOf(workTimeInSeconds / 3600));
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getProjectTotalCost(TimePeriod timePeriod, Project project) {
        return project.getUsers().stream()
                .map(user -> UserUtil.getUserCostInProject(project, user, timePeriod))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static double getProjectTotalWorktime(TimePeriod timePeriod, Project project) {
        return project.getUsers().stream()
                .map(user -> UserUtil.getUserTimeInProject(project, user, timePeriod))
                .mapToDouble(Double::doubleValue).sum();
    }
}