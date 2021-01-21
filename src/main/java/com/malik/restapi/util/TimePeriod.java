package com.malik.restapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Getter
@AllArgsConstructor
public enum TimePeriod {

    ALL(
            LocalDate.ofYearDay(2010, 1).atStartOfDay(),
            LocalDateTime.now()),

    CURRENT_YEAR(
            LocalDate.now().with(firstDayOfYear()).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().with(lastDayOfYear()), LocalTime.MAX)),

    CURRENT_MONTH(
            LocalDate.now().with(firstDayOfMonth()).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().with(lastDayOfMonth()), LocalTime.MAX)),

    CURRENT_WEEK(
            LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay(),
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.MAX));

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
}