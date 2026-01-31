package com.samyak.projectmanager.dto.response;

import com.samyak.projectmanager.entity.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDetailsDto {

    private Long id;
    private String name;
    private String description;
    private TeamRole yourRole;
    private Long membersCount;
}
