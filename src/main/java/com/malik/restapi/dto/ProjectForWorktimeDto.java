package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectForWorktimeDto {

    private UUID uuid;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}