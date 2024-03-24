package backend.taskweaver.domain.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamLeaderRequest {
    @Getter
    // 팀장 권한 바꾸기
    public static class ChangeLeaderRequest {

        @Schema(description = "변경할 팀장 id", example = "1")
        Long new_leader_id;

    }
}
