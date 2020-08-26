package com.malik.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private UUID uuid;
    private String login;
    private String name;
    private String surname;
    private String type;
    private String email;
    private BigDecimal salaryPerHour;
}