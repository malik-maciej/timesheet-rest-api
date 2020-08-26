package com.malik.restapi.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
public class UserCreateForm {

    @NotBlank
    @Size(max = 255)
    private String login;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String surname;

    @NotBlank
    @Size(max = 255)
    private String type;

    @NotBlank
    @Size(max = 255)
    private String password;

    @Email
    @Size(max = 254)
    private String email;

    @Positive
    private BigDecimal salaryPerHour;
}