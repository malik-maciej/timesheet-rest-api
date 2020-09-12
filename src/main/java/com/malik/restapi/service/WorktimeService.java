package com.malik.restapi.service;

import com.malik.restapi.dto.WorktimeDto;
import com.malik.restapi.form.WorktimeCreateForm;
import com.malik.restapi.model.Worktime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WorktimeService {

    List<WorktimeDto> getAllWorktimes(final UUID uuid);

    Worktime createWorktime(final UUID uuid, final WorktimeCreateForm createForm);

    void updateWorktime(final UUID uuid, final WorktimeCreateForm createForm);

    void deleteWorktime(final UUID uuid);
}