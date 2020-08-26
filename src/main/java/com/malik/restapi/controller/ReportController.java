package com.malik.restapi.controller;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.service.ProjectReportService;
import com.malik.restapi.service.UserReportService;
import com.malik.restapi.timeperiod.TimePeriod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
class ReportController {

    private final UserReportService userReportService;
    private final ProjectReportService projectReportService;

    ReportController(UserReportService userReportService, ProjectReportService projectReportService) {
        this.userReportService = userReportService;
        this.projectReportService = projectReportService;
    }

    @GetMapping("/users/{uuid}/report")
    @ResponseStatus(value = HttpStatus.OK)
    UserReportDto getUserReport(@PathVariable UUID uuid, @RequestParam TimePeriod timePeriod) {
        return userReportService.getReport(uuid, timePeriod);
    }

    @GetMapping("/projects/{uuid}/report")
    @ResponseStatus(value = HttpStatus.OK)
    ProjectReportDto getProjectReport(@PathVariable UUID uuid, @RequestParam TimePeriod timePeriod) {
        return projectReportService.getReport(uuid, timePeriod);
    }
}