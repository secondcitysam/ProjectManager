package com.samyak.projectmanager.controller.ui;

import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

}
