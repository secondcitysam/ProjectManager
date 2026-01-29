package com.samyak.projectmanager.controller.team;

import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.service.team.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ApiResponse<?> createTeam(
            @Valid @RequestBody CreateTeamRequest request
    ) {
        teamService.createTeam(request);
        return ApiResponse.successMessage("Team created successfully");
    }

    @PostMapping("/{teamId}/members")
    public ApiResponse<?> addMember(
            @PathVariable Long teamId,
            @RequestParam String userIdentifier
    ) {
        teamService.addMember(teamId, userIdentifier);
        return ApiResponse.successMessage("Member added successfully");
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ApiResponse<?> removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ) {
        teamService.removeMember(teamId, userId);
        return ApiResponse.successMessage("Member removed successfully");
    }
}
