package com.malik.restapi.timeperiod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class CurrentYear implements TypeOfPeriod {

    @Override
    public WorktimePeriod getWorktimePeriod() {
        LocalDate now = LocalDate.now();
        LocalDateTime from = LocalDateTime.of(now.withDayOfYear(1), LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(now.withDayOfYear(1).plusYears(1).minusDays(1), LocalTime.MAX);
        return new WorktimePeriod(from, to);
    }
}