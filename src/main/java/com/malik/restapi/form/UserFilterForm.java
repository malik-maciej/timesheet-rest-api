package com.malik.restapi.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UserFilterForm {

    @Size(max = 255)
    private String login;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    @Size(max = 255)
    private String type;

    @Positive
    private BigDecimal minSalary;

    @Positive
    private BigDecimal maxSalary;
}