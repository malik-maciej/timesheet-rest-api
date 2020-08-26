package com.malik.restapi.timeperiod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class AllTime implements TypeOfPeriod {

    @Override
    public WorktimePeriod getWorktimePeriod() {
        LocalDateTime from = LocalDateTime.of(LocalDate.ofYearDay(2000, 1), LocalTime.MIN);
        LocalDateTime to = LocalDateTime.now();
        return new WorktimePeriod(from, to);
    }
}