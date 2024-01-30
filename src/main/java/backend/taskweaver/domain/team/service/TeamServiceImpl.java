package backend.taskweaver.domain.team.service;

import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.team.dto.TeamInviteRequest;
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
public class TeamServiceImpl implements TeamService{
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final MemberRepository memberRepository;

    // 우선 팀 생성자 필드로만 추가
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreateRequest request, Long user) {
        Team team =  teamRepository.save(TeamConverter.toTeam(request));
        team.setTeamLeader(user);
        return TeamConverter.toCreateResponse(team);
    }

    public TeamInviteRequest.EmailInviteRequest inviteEmail(TeamInviteRequest.EmailInviteRequest request) {
        return request;
    }
}
