package com.samyak.projectmanager.service.project;

import com.samyak.projectmanager.config.security.SecurityUtils;
import com.samyak.projectmanager.dto.request.CreateProjectRequest;
import com.samyak.projectmanager.entity.*;
import com.samyak.projectmanager.repository.ProjectLifecycleEventRepository;
import com.samyak.projectmanager.repository.ProjectRepository;
import com.samyak.projectmanager.repository.TeamMemberRepository;
import com.samyak.projectmanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProjectLifecycleEventRepository lifecycleEventRepository;

    @Override
    public void createProject(Long teamId, CreateProjectRequest request) {

        // ---- AUDIT POINT #1: current user ----
        User currentUser = SecurityUtils.getCurrentUser();


        // ---- AUDIT POINT #2: leader validation ----
        boolean isLeader = teamMemberRepository
                .existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
                        teamId,
                        currentUser.getId(),
                        TeamRole.LEADER
                );

        if (!isLeader) {
            throw new RuntimeException("Only team leader can create projects");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // ---- Create project ----
        Project project = Project.builder()
                .team(team)
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(currentUser.getId())
                .build();

        projectRepository.save(project);

        // ---- AUDIT POINT #3: lifecycle event ----
        ProjectLifecycleEvent event = ProjectLifecycleEvent.builder()
                .project(project)
                .eventType(ProjectLifecycleEventType.CREATED)
                .performedBy(currentUser.getId())
                .note("Project created")
                .build();

        lifecycleEventRepository.save(event);
    }

    @Override
    public void archiveProject(Long projectId, String reason) {

        User currentUser = SecurityUtils.getCurrentUser();


        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // ---- AUDIT POINT #4: leader validation via team ----
        boolean isLeader = teamMemberRepository
                .existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
                        project.getTeam().getId(),
                        currentUser.getId(),
                        TeamRole.LEADER
                );

        if (!isLeader) {
            throw new RuntimeException("Only team leader can archive project");
        }

        if (project.getStatus() == ProjectStatus.ARCHIVED) {
            throw new RuntimeException("Project already archived");
        }

        // ---- State transition ----
        project.setStatus(ProjectStatus.ARCHIVED);
        project.setArchivedAt(java.time.LocalDateTime.now());
        project.setArchivedBy(currentUser.getId());

        projectRepository.save(project);

        // ---- AUDIT EVENT ----
        ProjectLifecycleEvent event = ProjectLifecycleEvent.builder()
                .project(project)
                .eventType(ProjectLifecycleEventType.ARCHIVED)
                .performedBy(currentUser.getId())
                .note(reason)
                .build();

        lifecycleEventRepository.save(event);
    }

    @Override
    public void restoreProject(Long projectId) {

        User currentUser = SecurityUtils.getCurrentUser();


        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean isLeader = teamMemberRepository
                .existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
                        project.getTeam().getId(),
                        currentUser.getId(),
                        TeamRole.LEADER
                );

        if (!isLeader) {
            throw new RuntimeException("Only team leader can restore project");
        }

        if (project.getStatus() == ProjectStatus.ACTIVE) {
            throw new RuntimeException("Project already active");
        }

        // ---- State transition ----
        project.setStatus(ProjectStatus.ACTIVE);
        project.setArchivedAt(null);
        project.setArchivedBy(null);

        projectRepository.save(project);

        // ---- AUDIT EVENT ----
        ProjectLifecycleEvent event = ProjectLifecycleEvent.builder()
                .project(project)
                .eventType(ProjectLifecycleEventType.RESTORED)
                .performedBy(currentUser.getId())
                .note("Project restored")
                .build();

        lifecycleEventRepository.save(event);
    }
}
