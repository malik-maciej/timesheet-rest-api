package com.malik.restapi.service;

import com.malik.restapi.dto.ProjectReportDto;
import com.malik.restapi.util.TimePeriod;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProjectReportService {

    ProjectReportDto getReport(final UUID uuid, final TimePeriod timePeriod);
}