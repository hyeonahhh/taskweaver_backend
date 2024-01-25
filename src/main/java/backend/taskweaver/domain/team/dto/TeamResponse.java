package backend.taskweaver.domain.team.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TeamResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class teamCreateResult {
        Long id;
        String name;
        String description;
        String inviteLink;
        Long teamLeader;
        LocalDateTime createdAt;
    }

}
