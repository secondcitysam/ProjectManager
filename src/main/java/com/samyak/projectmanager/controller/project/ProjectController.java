package com.samyak.projectmanager.controller.project;

import com.samyak.projectmanager.dto.request.CreateProjectRequest;
import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.entity.ProjectStatus;
import com.samyak.projectmanager.service.project.ProjectReadService;
import com.samyak.projectmanager.service.project.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectReadService projectReadService;

    // ---- CREATE ----
    @PostMapping("/team/{teamId}")
    public ApiResponse<?> createProject(
            @PathVariable Long teamId,
            @Valid @RequestBody CreateProjectRequest request
    ) {
        projectService.createProject(teamId, request);
        return ApiResponse.successMessage("Project created successfully");
    }

    // ---- READ (LIST) ----
    @GetMapping
    public ApiResponse<?> listProjects(
            @RequestParam(defaultValue = "ACTIVE") ProjectStatus status,
            Pageable pageable
    ) {
        return ApiResponse.success(
                projectReadService.getProjectsForCurrentUser(status, pageable)
        );
    }

    // ---- ARCHIVE ----
    @PostMapping("/{projectId}/archive")
    public ApiResponse<?> archiveProject(
            @PathVariable Long projectId,
            @RequestParam(required = false) String reason
    ) {
        projectService.archiveProject(projectId, reason);
        return ApiResponse.successMessage("Project archived successfully");
    }

    // ---- RESTORE ----
    @PostMapping("/{projectId}/restore")
    public ApiResponse<?> restoreProject(
            @PathVariable Long projectId
    ) {
        projectService.restoreProject(projectId);
        return ApiResponse.successMessage("Project restored successfully");
    }
}
