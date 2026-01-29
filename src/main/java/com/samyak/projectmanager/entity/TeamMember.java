package com.samyak.projectmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "team_members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"team_id", "user_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamRole role;

    @Column(nullable = false)
    private Boolean isActive;

    private LocalDateTime joinedAt;
    private LocalDateTime removedAt;

    @PrePersist
    public void prePersist() {
        if (joinedAt == null) {
            joinedAt = LocalDateTime.now();
        }

        if (isActive == null) {
            isActive = true;
        }
    }
}
