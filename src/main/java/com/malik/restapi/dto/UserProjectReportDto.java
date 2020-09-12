package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectReportDto {

    private UUID uuid;
    private String name;
    private BigDecimal employeeCost;
    private double employeeWorktimeHours;
}