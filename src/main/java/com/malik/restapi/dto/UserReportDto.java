package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReportDto {

    private UUID uuid;
    private String login;
    private BigDecimal totalCost;
    private List<UserProjectReportDto> projects;
}