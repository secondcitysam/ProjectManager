package com.samyak.projectmanager.service.project;

import com.samyak.projectmanager.dto.projection.ProjectListProjection;
import com.samyak.projectmanager.entity.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectReadService {

    Page<ProjectListProjection> getProjectsForCurrentUser(
            ProjectStatus status,
            Pageable pageable
    );
}
