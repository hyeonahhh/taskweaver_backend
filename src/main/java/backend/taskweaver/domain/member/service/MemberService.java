package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.member.dto.MemberInfoResponse;
import backend.taskweaver.domain.member.dto.UpdatePasswordRequest;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.repository.ProjectMemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.task.repository.TaskMemberRepository;
import backend.taskweaver.domain.task.repository.TaskRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.domain.team.service.TeamService;
import backend.taskweaver.domain.team.service.TeamServiceImpl;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.MemberConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final TaskMemberRepository taskMemberRepository;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long id) {
        return memberRepository.findById(id)
                .map(MemberConverter::toMemberInfoResponse)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public void updatePassword(Long memberId, UpdatePasswordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호가 같은지 확인
        if (!encoder.matches(request.oldPassword(), member.getPassword())) {
            throw new BusinessExceptionHandler(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 현재 비밀번호와 같은 비밀번호로 변경 불가능
        if (request.oldPassword().equals(request.newPassword())) {
            throw new BusinessExceptionHandler(ErrorCode.SAME_PASSWORD);
        }

        member.updatePassword(encoder.encode(request.newPassword()));
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 팀 리더라면 탈퇴 불가
        List<Team> teams = teamRepository.findAllByTeamLeader(memberId);
        if(!teams.isEmpty()) {
            throw new BusinessExceptionHandler(ErrorCode.CANNOT_WITHDRAW_TEAM_LEADER);
        }

        // 속한 팀에서 탈퇴하기
        teamMemberRepository.deleteByMemberId(memberId);

        // 프로젝트 리더라면 탈퇴 불가
        List<Project> projects = projectRepository.findAllByManagerId(memberId);
        if(!projects.isEmpty()) {
            throw new BusinessExceptionHandler(ErrorCode.CANNOT_WITHDRAW_PROJECT_LEADER);
        }

        // 속한 프로젝트에서 탈퇴하기
        projectMemberRepository.deleteByMemberId(memberId);

        // 속한 태스크에서 탈퇴하기
        taskMemberRepository.deleteByMemberId(memberId); // managerId 삭제하기

        // 회원 탈퇴하기
        member.deleteSoftly();
    }
}
