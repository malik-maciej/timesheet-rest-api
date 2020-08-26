package com.malik.restapi.service;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.model.Worktime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WorktimeService {

    List<WorktimeDto> getAllWorktimes(UUID uuid);

    Worktime createWorktime(UUID uuid, WorktimeCreateForm createForm);

    void updateWorktime(UUID uuid, WorktimeCreateForm createForm);

    void deleteWorktime(UUID uuid);
}