package com.malik.restapi.util;

import com.malik.restapi.model.Project;
import com.malik.restapi.model.Worktime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public final class ProjectUtil {

    private ProjectUtil() {
    }

    public static BigDecimal getBudgetPercentage(final Project project) {
        return getCostOfProject(project)
                .multiply(BigDecimal.valueOf(100))
                .divide(project.getBudget(), 3, RoundingMode.CEILING);
    }

    private static BigDecimal getCostOfProject(Project project) {
        return project.getWorktimes().stream()
                .map(ProjectUtil::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getTotalCost(final Worktime worktime) {
        final BigDecimal userSalaryPerHour = worktime.getUser().getSalaryPerHour();
        final double workTimeInSeconds = Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds();
        return userSalaryPerHour.multiply(BigDecimal.valueOf(workTimeInSeconds / 3600));
    }

    public static BigDecimal getProjectTotalCost(final TimePeriod timePeriod, final Project project) {
        return project.getUsers().stream()
                .map(user -> UserUtil.getUserCostInProject(project, user, timePeriod))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static double getProjectTotalWorktime(final TimePeriod timePeriod, final Project project) {
        return project.getUsers().stream()
                .map(user -> UserUtil.getUserTimeInProject(project, user, timePeriod))
                .mapToDouble(Double::doubleValue).sum();
    }
}