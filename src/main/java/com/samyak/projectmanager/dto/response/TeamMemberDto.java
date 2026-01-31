package com.samyak.projectmanager.dto.response;

import com.samyak.projectmanager.entity.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamMemberDto {

    private Long userId;
    private String username;
    private TeamRole role;
}
