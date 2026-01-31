package com.samyak.projectmanager.service.team;

import com.samyak.projectmanager.dto.projection.TeamSummaryProjection;
import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.dto.response.TeamDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    void createTeam(CreateTeamRequest request);

    void addMember(Long teamId, String userIdentifier);

    void removeMember(Long teamId, Long userId);


    // ✅ ADD THIS
    Page<TeamSummaryProjection> getTeamsForCurrentUser(Pageable pageable);

    TeamDetailsDto getTeamDetails(Long teamId);
}
