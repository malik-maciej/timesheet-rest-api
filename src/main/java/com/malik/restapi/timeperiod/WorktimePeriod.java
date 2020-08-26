package com.malik.restapi.timeperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WorktimePeriod {

    private LocalDateTime from;
    private LocalDateTime to;
}