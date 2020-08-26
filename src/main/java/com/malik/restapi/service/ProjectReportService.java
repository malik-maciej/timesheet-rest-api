package com.malik.restapi.service;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.timeperiod.TimePeriod;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProjectReportService {

    ProjectReportDto getReport(UUID uuid, TimePeriod timePeriod);
}