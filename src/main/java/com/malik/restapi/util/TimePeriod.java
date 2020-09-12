package com.malik.restapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum TimePeriod {

    ALL(
            LocalDate.ofYearDay(2010, 1).atStartOfDay(),
            LocalDateTime.now()),

    CURRENT_YEAR(
            LocalDate.now().withDayOfYear(1).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().withDayOfYear(1).plusYears(1).minusDays(1), LocalTime.MAX)),

    CURRENT_MONTH(
            LocalDate.now().withDayOfMonth(1).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1), LocalTime.MAX)),

    CURRENT_WEEK(
            LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.MAX));

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
}