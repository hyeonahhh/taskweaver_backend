package backend.taskweaver.domain.team.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.team.dto.TeamInviteRequest;
import backend.taskweaver.domain.team.dto.TeamInviteResponse;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;
import backend.taskweaver.domain.team.entity.TeamMemberState;
import backend.taskweaver.domain.team.entity.enums.InviteState;
import backend.taskweaver.domain.team.entity.enums.TeamRole;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamMemberStateRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.TeamConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final TeamMemberRepository teamMemberRepository;

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
        // 이메일 형식이 아닐 경우 추가
        Optional<Member> matchingMember = members.stream()
                .filter(member -> member.getEmail().equals(request.getEmail()))
                .findFirst();

        if (matchingMember.isPresent()) {
            Long userId = matchingMember.get().getId();
            System.out.println(userId);

            Long teamId = request.getTeam_id();

            TeamMemberState teamMemberState = TeamMemberState.builder()
                    .member(matchingMember.get())
                    .state(InviteState.IN_PROGRESS)
                    .team(teamRepository.findById(teamId)
                            .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND)))
                    .build();
            teamMemberStateRepository.save(teamMemberState);
        } else {
            // 처리 필요
            throw new BusinessExceptionHandler(ErrorCode.TEAM_MEMBER_NOT_FOUND);
        }
        return request;
    }


    // 초대 응답
    public TeamInviteResponse.InviteAnswerResult answerInvite(TeamInviteRequest.InviteAnswerRequest request, Long user) {
        Long teamId = request.getTeam_id();
        Long userId = user;
        System.out.println(userId);
        System.out.println(user);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .member(member)
                .role(TeamRole.MEMBER) // 직접 MEMBER를 사용
                .build();

        teamMemberRepository.save(teamMember);

        return TeamConverter.toInviteResponse(teamMember);
    }

}
