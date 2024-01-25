package backend.taskweaver.domain.team.dto;

import lombok.*;


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
    }

}
