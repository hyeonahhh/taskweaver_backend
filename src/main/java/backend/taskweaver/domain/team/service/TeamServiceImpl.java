package backend.taskweaver.domain.team.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.team.dto.TeamInviteRequest;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMemberState;
import backend.taskweaver.domain.team.entity.enums.InviteState;
import backend.taskweaver.domain.team.repository.TeamMemberStateRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.TeamConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import backend.taskweaver.global.exception.handler.GlobalExceptionHandler;
import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final TeamMemberStateRepository teamMemberStateRepository;

    // 팀 생성
    // 우선 팀 생성자 필드로만 추가
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreateRequest request, Long user) {
        Team team =  teamRepository.save(TeamConverter.toTeam(request));
        team.setTeamLeader(user);
        return TeamConverter.toCreateResponse(team);
    }

    // 팀 초대
    public TeamInviteRequest.EmailInviteRequest inviteEmail(TeamInviteRequest.EmailInviteRequest request) {
        List<Member> members = memberRepository.findAll();
        Optional<Member> matchingMember = members.stream()
                .filter(member -> member.getEmail().equals(request.getEmail()))
                .findFirst();

        if (matchingMember.isPresent()) {
            Long userId = matchingMember.get().getId();
            System.out.println(userId);
            // teamId 일단 고정해놓음(request body)로 받아야 함
            Team team = teamRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Team not found with ID: 1"));
            TeamMemberState teamMemberState = TeamMemberState.builder()
                    .member(matchingMember.get())
                    .state(InviteState.valueOf("IN_PROGRESS"))
                    .team(team)
                    .build();
            teamMemberStateRepository.save(teamMemberState);
        } else {
            // 처리 필요
            throw new BusinessExceptionHandler(ErrorCode.NOT_EXIST_MEMBER);
        }
        return request;
    }
}
