package com.samyak.projectmanager.service.project;

import com.samyak.projectmanager.dto.request.CreateProjectRequest;

public interface ProjectService {

    void createProject(Long teamId, CreateProjectRequest request);

    void archiveProject(Long projectId, String reason);

    void restoreProject(Long projectId);
}
