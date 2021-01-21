package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProjectReportDto {

    private UUID uuid;
    private String name;
    private BigDecimal totalCost;
    private double totalWorktimeHours;
    private boolean budgetExceeded;
    private List<UserForProjectReportDto> users;
}