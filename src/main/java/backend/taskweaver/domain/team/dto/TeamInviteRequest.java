package backend.taskweaver.domain.team.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamInviteRequest {

    // 이메일 초대 요청
    @Getter
    public static class EmailInviteRequest {
        @Schema(description = "초대할 유저 이메일", example = "xxx@naver.com")
        String email;

        @Schema(description = "해당 팀 id", example = "1")
        Long team_id;

    }

    // 이메일 초대 응답
    @Getter
    public static class InviteAnswerRequest {

        @Schema(description = "해당 팀 id", example = "1")
        Long team_id;

        @Schema(description = "수락/거절 여부", example = "1")
        Long inviteState;
    }
}
