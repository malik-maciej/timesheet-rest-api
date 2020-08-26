package com.malik.restapi.timeperiod;

public enum TimePeriod {

    ALL(new AllTime()),
    CURRENT_YEAR(new CurrentYear()),
    CURRENT_MONTH(new CurrentMonth()),
    CURRENT_WEEK(new CurrentWeek());

    private final TypeOfPeriod typeOfPeriod;

    TimePeriod(TypeOfPeriod typeOfPeriod) {
        this.typeOfPeriod = typeOfPeriod;
    }

    public static WorktimePeriod getWorktimePeriod(TimePeriod timePeriod) {
        return timePeriod.typeOfPeriod.getWorktimePeriod();
    }
}