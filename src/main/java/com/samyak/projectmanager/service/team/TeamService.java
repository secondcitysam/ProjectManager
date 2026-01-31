package com.samyak.projectmanager.service.team;

import com.samyak.projectmanager.dto.projection.TeamSummaryProjection;
import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.dto.response.TeamDetailsDto;
import com.samyak.projectmanager.dto.response.TeamMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    void createTeam(CreateTeamRequest request);

    void addMember(Long teamId, String userIdentifier);

    void removeMember(Long teamId, Long userId);

    Page<TeamSummaryProjection> getTeamsForCurrentUser(Pageable pageable);

    TeamDetailsDto getTeamDetails(Long teamId);

    // ✅ NEW — for team members page
    List<TeamMemberDto> getTeamMembers(Long teamId);
}
