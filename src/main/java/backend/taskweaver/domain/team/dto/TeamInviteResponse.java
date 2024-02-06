package backend.taskweaver.domain.team.dto;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.enums.TeamRole;
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
        Long id;
        TeamRole role;
        Long team_id;
        Long member_id;
    }
}
