package backend.taskweaver.domain.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class TeamLeaderResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeLeaderResponse{

        @Schema(description = "팀 id", example = "1")
        Long team_id;

        @Schema(description = "변경된 팀장 id", example = "1")
        Long new_leader_id;

    }

}
