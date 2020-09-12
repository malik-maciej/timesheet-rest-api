package com.malik.restapi.factory;

import com.malik.restapi.form.UserCreateForm;
import com.malik.restapi.model.User;
import com.malik.restapi.util.UserType;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.math.BigDecimal;

public class UserFactory {

    public static User getUser() {
        final String login = RandomString.make();
        final User user = new User();
        user.setLogin(login);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail(login + "@mail.com");
        user.setPassword(RandomString.make());
        user.setType(UserType.MANAGER);
        user.setSalaryPerHour(BigDecimal.valueOf(50));
        return user;
    }

    public static UserCreateForm getUserCreateForm() {
        final String login = RandomString.make();
        final UserCreateForm ucf = new UserCreateForm();
        ucf.setLogin(login);
        ucf.setName("Mark");
        ucf.setSurname("Smith");
        ucf.setEmail(login + "@mail.com");
        ucf.setPassword(RandomString.make());
        ucf.setType("ADMIN");
        ucf.setSalaryPerHour(BigDecimal.valueOf(100));
        return ucf;
    }
}
