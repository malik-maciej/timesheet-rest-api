package com.malik.restapi.form;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectFilterForm {

    @Size(max = 255)
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> users;
    private Boolean budgetExceeded;
}