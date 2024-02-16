package backend.taskweaver.domain.team.dto;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.enums.TeamRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TeamInviteResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InviteAnswerResult {
        @Schema(description = "초대 응답 id", example = "1")
        Long id;

        @Schema(description = "초대된 유저 역할", example = "MEMBER")
        TeamRole role;

        @Schema(description = "초대한 팀 id", example = "1")
        Long team_id;

        @Schema(description = "초대한 유저 id", example = "1")
        Long member_id;
    }
}
