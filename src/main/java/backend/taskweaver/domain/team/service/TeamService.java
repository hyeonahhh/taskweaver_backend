package backend.taskweaver.domain.team.service;


import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamRepository;

import backend.taskweaver.global.converter.TeamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TeamService {
    @Autowired
    private final TeamRepository teamRepository;
    // 우선 팀 생성자 필드로만 추가
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreate request, Long user) {
        Team team =  teamRepository.save(TeamConverter.toTeam(request));
        team.setTeamLeader(user);
        return TeamConverter.toCreateResponse(team);

    }
}
