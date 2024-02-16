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
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberStateRepository teamMemberStateRepository;
    private final TeamMemberRepository teamMemberRepository;

    // 팀 생성
    // 우선 팀 생성자 필드로만 추가
    public TeamResponse.teamCreateResult createTeam(TeamRequest.teamCreateRequest request, Long user) {
        Team team =  teamRepository.save(TeamConverter.toTeam(request));
        team.setTeamLeader(user);
        return TeamConverter.toCreateResponse(team);
    }

    // 해당 팀 조회
    public TeamResponse.findTeamResult findTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));

        List<TeamMember> teamMembers = findAllTeamMemberWithTeam(id);

        return TeamConverter.toGetTeamResponse(team, teamMembers);
    }

    public List<TeamMember> findAllTeamMemberWithTeam(Long teamId) {
        return teamMemberRepository.findAllByTeamId(teamId);
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

            Long teamId = request.getTeam_id();

            // 이미 존재하는 팀 초대 요청인 경우
            if (teamMemberStateRepository.existsByTeamIdAndMemberId(teamId, userId)) {
                throw new BusinessExceptionHandler(ErrorCode.INVITATION_ALREADY_SENT);
            }

            TeamMemberState teamMemberState = TeamMemberState.builder()
                    .member(matchingMember.get())
                    .state(InviteState.IN_PROGRESS)
                    .team(teamRepository.findById(teamId)
                            .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND)))
                    .build();
            teamMemberStateRepository.save(teamMemberState);
        } else {
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


        // 초대 수락/거절 여부 확인
        if (request.getInviteState() == 1) {
            TeamMember teamMember = TeamMember.builder()
                    .team(team)
                    .member(member)
                    .role(TeamRole.MEMBER)
                    .build();

            teamMemberRepository.save(teamMember);

            // 초대 응답 후에 해당 team id와 user id가 일치하는 값을 TeamMemberState에서 찾아서 inviteState 값을 ACCEPT로 바꾸기
            acceptInvite(teamId, userId);

            return TeamConverter.toInviteResponse(teamMember);
        } else {
            // 초대를 수락하지 않은 경우 null 값 반환 수정 필요
            refuseInvite(teamId, userId);
            return null;
        }

    }

    // 초대 응답 수락 시에 해당 team id와 user id가 일치하는 값을 TeamMemberState에서 찾아서 inviteState 값을 ACCEPT로 바꾸는 메소드
    private void acceptInvite(Long teamId, Long userId) {
        Optional<TeamMemberState> teamMemberStateOptional = teamMemberStateRepository.findByTeamIdAndMemberId(teamId, userId);
        teamMemberStateOptional.ifPresentOrElse(teamMemberState -> {
            teamMemberState.setState(InviteState.ACCEPT);
            teamMemberStateRepository.save(teamMemberState);
        }, () -> {
            // teamId와 userId가 일치하는 값을 찾지 못한 경우
            throw new BusinessExceptionHandler(ErrorCode.TEAM_MEMBER_STATE_NOT_FOUND);
        });
    }

    // 초대 응답 거부
    private void refuseInvite(Long teamId, Long userId) {
        Optional<TeamMemberState> teamMemberStateOptional = teamMemberStateRepository.findByTeamIdAndMemberId(teamId, userId);
        teamMemberStateOptional.ifPresentOrElse(teamMemberState -> {
            teamMemberState.setState(InviteState.REFUSE);
            teamMemberStateRepository.save(teamMemberState);
        }, () -> {
            // teamId와 userId가 일치하는 값을 찾지 못한 경우
            throw new BusinessExceptionHandler(ErrorCode.TEAM_MEMBER_STATE_NOT_FOUND);
        });
    }


}
