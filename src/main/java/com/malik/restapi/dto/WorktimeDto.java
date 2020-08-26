package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorktimeDto {

    private UUID uuid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ProjectForWorktimeDto project;
}