package com.malik.restapi.service;

import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.timeperiod.TimePeriod;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserReportService {

    UserReportDto getReport(UUID uuid, TimePeriod timePeriod);
}