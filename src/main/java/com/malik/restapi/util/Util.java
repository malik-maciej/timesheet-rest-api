package com.malik.restapi.util;

import com.malik.restapi.model.Worktime;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class Util {

    public BigDecimal getRoundedSum(final BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.CEILING);
    }

    public double getRoundedSum(final double sum) {
        final BigDecimal bd = BigDecimal.valueOf(sum);
        return getRoundedSum(bd).doubleValue();
    }

    boolean isCorrectPeriod(final TimePeriod timePeriod, final Worktime worktime) {
        return worktime.getStartTime().isAfter(timePeriod.getPeriodStart())
                && worktime.getEndTime().isBefore(timePeriod.getPeriodEnd());
    }
}