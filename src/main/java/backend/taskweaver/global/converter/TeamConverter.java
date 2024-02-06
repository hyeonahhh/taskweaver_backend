package backend.taskweaver.global.converter;

import backend.taskweaver.domain.team.dto.TeamInviteResponse;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;

import java.util.UUID;

public class TeamConverter {

    public static Team toTeam(TeamRequest.teamCreateRequest request) {
        return Team.builder()
                .name(request.getName())
                .inviteLink(generateInviteLink())
                .build();
    }

    public static TeamResponse.teamCreateResult toCreateResponse(Team team) {
        return new TeamResponse.teamCreateResult(
            team.getId(),
            team.getName(),
            team.getInviteLink(),
            team.getTeamLeader(),
            team.getCreatedAt()
        );
    }

    public static TeamInviteResponse.InviteAnswerResult toInviteResponse(TeamMember teamMember) {
        return new TeamInviteResponse.InviteAnswerResult(
                teamMember.getId(),
                teamMember.getRole(),
                teamMember.getTeam().getId(),
                teamMember.getMember().getId()
        );
    }
    public static String generateInviteLink() {
        UUID uuid = UUID.randomUUID();
        // 도메인 결정 후
        return "https://localhost:" + "8081" + "/invite/" + uuid.toString();
    }

}
