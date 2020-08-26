package com.malik.restapi.factory;

import com.malik.restapi.form.ProjectCreateForm;
import com.malik.restapi.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectFactory {

    public static Project getProject() {
        Project project = new Project();
        project.setName("project1");
        project.setDescription("description for project1");
        project.setBudget(BigDecimal.valueOf(200000));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setEndDate(LocalDate.of(2020, 11, 30));
        return project;
    }

    public static ProjectCreateForm getProjectCreateForm() {
        ProjectCreateForm pcf = new ProjectCreateForm();
        pcf.setName("new project1");
        pcf.setDescription("new description for project1");
        pcf.setBudget(BigDecimal.valueOf(150000));
        pcf.setStartDate(LocalDate.of(2020, 2, 1));
        pcf.setEndDate(LocalDate.of(2020, 10, 30));
        return pcf;
    }
}
