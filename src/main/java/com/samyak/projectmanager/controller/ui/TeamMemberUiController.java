package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamMemberUiController {

    private final TeamService teamService;

    // ===============================
    // TEAM MEMBERS PAGE
    // ===============================
    @GetMapping("/{teamId}/members")
    public String teamMembers(
            @PathVariable Long teamId,
            Model model
    ) {

        model.addAttribute(
                "team",
                teamService.getTeamDetails(teamId)
        );

        model.addAttribute(
                "members",
                teamService.getTeamMembers(teamId)
        );

        return "team/team-members-page";
    }

    // ===============================
    // ADD MEMBER
    // ===============================
    @PostMapping("/{teamId}/members/add")
    public String addMember(
            @PathVariable Long teamId,
            @RequestParam String userIdentifier
    ) {

        teamService.addMember(teamId, userIdentifier);

        return "redirect:/teams/" + teamId + "/members";
    }

    // ===============================
    // REMOVE MEMBER
    // ===============================
    @PostMapping("/{teamId}/members/{userId}/remove")
    public String removeMember(
            @PathVariable Long teamId,
            @PathVariable Long userId
    ) {

        teamService.removeMember(teamId, userId);

        return "redirect:/teams/" + teamId + "/members";
    }
}
