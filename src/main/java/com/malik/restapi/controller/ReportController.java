package com.malik.restapi.controller;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.service.ProjectReportService;
import com.malik.restapi.service.UserReportService;
import com.malik.restapi.util.TimePeriod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class ReportController {

    private final UserReportService userReportService;
    private final ProjectReportService projectReportService;

    ReportController(final UserReportService userReportService, final ProjectReportService projectReportService) {
        this.userReportService = userReportService;
        this.projectReportService = projectReportService;
    }

    @GetMapping(Routes.USER_REPORT)
    UserReportDto getUserReport(@PathVariable final UUID uuid, @RequestParam final TimePeriod timePeriod) {
        return userReportService.getReport(uuid, timePeriod);
    }

    @GetMapping(Routes.PROJECT_REPORT)
    ProjectReportDto getProjectReport(@PathVariable final UUID uuid, @RequestParam final TimePeriod timePeriod) {
        return projectReportService.getReport(uuid, timePeriod);
    }

    private static class Routes {
        static final String USER_REPORT = "/users/{uuid}/report";
        static final String PROJECT_REPORT = "/projects/{uuid}/report";
    }
}