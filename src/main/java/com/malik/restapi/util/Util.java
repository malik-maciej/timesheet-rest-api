package com.malik.restapi.util;

import com.malik.restapi.model.Worktime;
import com.malik.restapi.timeperiod.TimePeriod;
import com.malik.restapi.timeperiod.WorktimePeriod;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Util {

    private Util() {
    }

    public static BigDecimal getRoundedSum(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.CEILING);
    }

    public static double getRoundedSum(double sum) {
        BigDecimal bd = BigDecimal.valueOf(sum);
        return getRoundedSum(bd).doubleValue();
    }

    static boolean isCorrectPeriod(TimePeriod timePeriod, Worktime worktime) {
        WorktimePeriod worktimePeriod = TimePeriod.getWorktimePeriod(timePeriod);
        return worktime.getStartTime().isAfter(worktimePeriod.getFrom())
                && worktime.getEndTime().isBefore(worktimePeriod.getTo());
    }
}