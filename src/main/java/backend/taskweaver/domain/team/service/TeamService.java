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
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreate request) {
        Team team =  teamRepository.save(TeamConverter.toTeam(request));
        return TeamConverter.toCreateResponse(team);

    }
}
