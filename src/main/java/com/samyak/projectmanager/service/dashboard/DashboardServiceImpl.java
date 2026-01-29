package com.samyak.projectmanager.service.dashboard;

import com.samyak.projectmanager.dto.response.DashboardSummaryResponse;
import com.samyak.projectmanager.entity.ProjectStatus;
import com.samyak.projectmanager.entity.User;
import com.samyak.projectmanager.repository.ProjectRepository;
import com.samyak.projectmanager.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;

    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        // ---- AUDIT POINT #1: current user ----
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        long teamsCount = teamMemberRepository
                .countByUserIdAndIsActiveTrue(currentUser.getId());

        long activeProjects = projectRepository
                .countProjectsForUserByStatus(currentUser.getId(), ProjectStatus.ACTIVE);

        long archivedProjects = projectRepository
                .countProjectsForUserByStatus(currentUser.getId(), ProjectStatus.ARCHIVED);

        LocalDateTime lastUpdate = projectRepository
                .findLastUpdatedProjectTime(currentUser.getId());

        return new DashboardSummaryResponse(
                teamsCount,
                activeProjects,
                archivedProjects,
                lastUpdate
        );
    }
}
