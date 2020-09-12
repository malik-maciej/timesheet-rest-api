package com.malik.restapi.service;

import com.malik.restapi.dto.UserReportDto;
import com.malik.restapi.util.TimePeriod;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserReportService {

    UserReportDto getReport(final UUID uuid, final TimePeriod timePeriod);
}