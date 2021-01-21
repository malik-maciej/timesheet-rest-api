package com.malik.restapi.util;

import com.malik.restapi.model.Project;
import com.malik.restapi.model.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Duration;

@UtilityClass
public class UserUtil {

    public BigDecimal getUserTotalCost(final TimePeriod timePeriod, final User user) {
        return user.getProjects().stream()
                .map(project -> getUserCostInProject(project, user, timePeriod))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal getUserCostInProject(final Project project, final User user, final TimePeriod timePeriod) {
        return getUserCostInProject(user, getUserTimeInProject(project, user, timePeriod));
    }

    public BigDecimal getUserCostInProject(final User user, final double timeInProject) {
        return user.getSalaryPerHour().multiply(BigDecimal.valueOf(timeInProject));
    }

    public double getUserTimeInProject(final Project project, final User user, final TimePeriod timePeriod) {
        return project.getWorktimes().stream()
                .filter(worktime -> worktime.getUser().equals(user))
                .filter(worktime -> Util.isCorrectPeriod(timePeriod, worktime))
                .map(worktime -> Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds())
                .mapToDouble(Long::longValue).sum() / 3600;
    }
}