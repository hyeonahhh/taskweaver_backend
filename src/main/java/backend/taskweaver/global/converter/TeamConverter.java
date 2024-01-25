package backend.taskweaver.global.converter;

import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;

public class TeamConverter {

    public static Team toTeam(TeamRequest.teamCreate request) {
        return Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static TeamResponse.teamCreateResult toCreateResponse(Team team) {
        return new TeamResponse.teamCreateResult(
            team.getId(),
            team.getName(),
            team.getDescription(),
            team.getInviteLink()
        );
    }



}
