package com.samyak.projectmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DashboardSummaryResponse {

    private long teamsCount;
    private long activeProjectsCount;
    private long archivedProjectsCount;
    private LocalDateTime lastProjectUpdateAt;
}
