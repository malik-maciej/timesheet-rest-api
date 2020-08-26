package com.malik.restapi.util;

import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import com.malik.restapi.timeperiod.TimePeriod;

import java.math.BigDecimal;
import java.time.Duration;

public final class UserUtil {

    private UserUtil() {
    }

    public static BigDecimal getUserTotalCost(TimePeriod timePeriod, User user) {
        return user.getProjects().stream()
                .map(project -> getUserCostInProject(project, user, timePeriod))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getUserCostInProject(Project project, User user, TimePeriod timePeriod) {
        return getUserCostInProject(user, getUserTimeInProject(project, user, timePeriod));
    }

    public static BigDecimal getUserCostInProject(User user, double timeInProject) {
        return user.getSalaryPerHour().multiply(BigDecimal.valueOf(timeInProject));
    }

    public static double getUserTimeInProject(Project project, User user, TimePeriod timePeriod) {
        return project.getWorktimes().stream()
                .filter(worktime -> worktime.getUser().equals(user))
                .filter(worktime -> Util.isCorrectPeriod(timePeriod, worktime))
                .map(worktime -> Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds())
                .mapToDouble(Long::longValue).sum() / 3600;
    }
}