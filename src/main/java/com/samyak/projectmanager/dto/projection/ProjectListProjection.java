package com.samyak.projectmanager.dto.projection;

import com.samyak.projectmanager.entity.ProjectStatus;

import java.time.LocalDateTime;

public interface ProjectListProjection {

    Long getId();
    String getName();
    ProjectStatus getStatus();
    LocalDateTime getUpdatedAt();
    Long getCreatedBy();
}
