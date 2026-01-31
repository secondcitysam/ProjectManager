package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TeamUiController {

    private final TeamService teamService;

    // ===============================
    // TEAM LIST
    // ===============================
    @GetMapping("/teams")
    public String teamList(Model model) {

        model.addAttribute(
                "teams",
                teamService
                        .getTeamsForCurrentUser(PageRequest.of(0, 20))
                        .getContent()
        );

        return "team/team-page";
    }

    // ===============================
    // CREATE TEAM (PAGE)
    // ===============================
    @GetMapping("/teams/create")
    public String createTeamPage() {
        return "team/create-team-page";
    }

    // ===============================
    // CREATE TEAM (ACTION)
    // ===============================
    @PostMapping("/teams/create")
    public String createTeam(
            @ModelAttribute CreateTeamRequest request
    ) {

        teamService.createTeam(request);

        return "redirect:/teams";
    }

    @GetMapping("/teams/{id}")
    public String teamDetails(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "team",
                teamService.getTeamDetails(id)
        );

        return "team/team-details-page";
    }
    @GetMapping("/teams/{id}/members")
    public String teamMembers(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("teamId", id);
        model.addAttribute(
                "members",
                teamService.getTeamMembers(id)
        );

        return "team/team-members-page";
    }


    @PostMapping("/teams/{id}/members/add")
    public String addMember(
            @PathVariable Long id,
            @RequestParam String username
    ) {
        teamService.addMember(id, username);
        return "redirect:/teams/" + id + "/members";
    }

    @PostMapping("/teams/{id}/members/{userId}/remove")
    public String removeMember(
            @PathVariable Long id,
            @PathVariable Long userId
    ) {
        teamService.removeMember(id, userId);
        return "redirect:/teams/" + id + "/members";
    }


}
