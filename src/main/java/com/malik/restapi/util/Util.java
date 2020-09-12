package com.malik.restapi.util;

import com.malik.restapi.model.Worktime;

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
        return worktime.getStartTime().isAfter(timePeriod.getPeriodStart())
                && worktime.getEndTime().isBefore(timePeriod.getPeriodEnd());
    }
}