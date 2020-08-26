package com.malik.restapi.timeperiod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class CurrentWeek implements TypeOfPeriod {

    @Override
    public WorktimePeriod getWorktimePeriod() {
        LocalDate now = LocalDate.now();
        LocalDateTime from = LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(from.toLocalDate().plusDays(6), LocalTime.MAX);
        return new WorktimePeriod(from, to);
    }
}