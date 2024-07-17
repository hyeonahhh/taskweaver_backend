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
    private final TaskRepository taskRepository;

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
        // 팀 리더라면 팀 리더 필드를 null로 업데이트한다
        List<Team> teams = teamRepository.findAllByTeamLeader(memberId);
        for (Team team : teams) {
            team.setTeamLeader(null);
        }
        // 속한 팀들에서 삭제하기
        teamMemberRepository.deleteByMemberId(memberId);

        // 팀장이 탈퇴히면 어떻게 되는지?


        // 프로젝트 리더라면 프로젝트 리더 필드를 null로 업데이트한다.
        List<Project> projects = projectRepository.findAllByManagerId(memberId);
        for (Project project : projects) {
            project.setManager(null, null);
        }

        // 프로젝트에서 회원 삭제하기
        projectMemberRepository.deleteByMemberId(memberId);

        // 프로젝트 팀장이 탈퇴하면 어떻게 되는지?


        // 태스크에서 회원 삭제하기
        taskMemberRepository.deleteByMemberId(memberId); // managerId 삭제하기

        // 회원 삭제
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
        member.deleteSoftly();
    }
}
