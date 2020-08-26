package com.malik.restapi.timeperiod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class CurrentMonth implements TypeOfPeriod {

    @Override
    public WorktimePeriod getWorktimePeriod() {
        LocalDate now = LocalDate.now();
        LocalDateTime from = LocalDateTime.of(now.withDayOfMonth(1), LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(now.withDayOfMonth(1).plusMonths(1).minusDays(1), LocalTime.MAX);
        return new WorktimePeriod(from, to);
    }
}