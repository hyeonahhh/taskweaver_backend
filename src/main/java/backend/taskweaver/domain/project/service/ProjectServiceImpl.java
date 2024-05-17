package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.member.entity.DeviceToken;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.DeviceTokenRepository;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.dto.*;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.project.repository.ProjectMemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.ProjectConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import backend.taskweaver.global.firebase.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final FcmService fcmService;
    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, Long teamId) throws IOException {
        // project 저장
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        Project project = ProjectConverter.toProject(request, team);
        projectRepository.save(project);

        // project member 저장
        List<Member> members = createProjectMember(project, request);

        // 푸시 알림 보내기
        sendProjectNotification(members, project, request.managerId());

        return ProjectConverter.toProjectResponse(project, request.memberIdList());
    }

    private List<Member> createProjectMember(Project project, ProjectRequest request) {
        // 담당자 id가 member id list에 있는지 확인
        if (!request.memberIdList().contains(request.managerId())) {
            throw new BusinessExceptionHandler(ErrorCode.MANAGER_ID_NOT_IN_MEMBER_ID_LIST);
        }

        List<ProjectMember> projectMembers = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        request.memberIdList().forEach(memberId -> {
            // 해당 회원이 존재하는지 확인
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
            members.add(member);

            // 해당 회원이 해당 팀에 존재하는지 확인
            teamMemberRepository.findByTeamIdAndMemberId(project.getTeam().getId(), memberId)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_BELONG_TO_TEAM));

            // 프로젝트 담당자면 담당자 설정
            if (memberId.equals(request.managerId())) {
                project.setManager(memberId, member.getNickname());
            }

            // 프로젝트 멤버 저장
            ProjectMember projectMember = ProjectConverter.toProjectMember(project, member);
            projectMembers.add(projectMember);
        });

        projectMemberRepository.saveAll(projectMembers);
        return members;
    }

    private void sendProjectNotification(List<Member> members, Project project, Long managerId) throws IOException {
        String nickname = memberRepository.findById(managerId).get().getNickname();
        ProjectNotificationMessage notificationMessage = new ProjectNotificationMessage(project.getTeam().getName(), project.getName(), nickname);

        List<DeviceToken> deviceTokens = deviceTokenRepository.findAllByMemberIn(members);
        for (DeviceToken deviceToken : deviceTokens) {
            fcmService.sendMessageTo(deviceToken.getDeviceToken(), notificationMessage.getCreateMessage());
        }
    }

    @Override
    @Transactional
    public void updateProject(Long projectId, ProjectRequest request, Long memberId) throws IOException {
        // 프로젝트 존재하는지 확인
        Project project = validateProject(projectId);

        // 현재 로그인한 사람이 프로젝트 담당자인지 확인
        checkIfIsManager(project.getManagerId(), memberId);

        project.updateProject(request);
        projectMemberRepository.deleteAllByProject(project);
        createProjectMember(project, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAll(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        List<Project> projects = projectRepository.findAllByTeam(team);
        return projects.stream()
                .map(project -> ProjectConverter.toProjectResponse(project, null))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getOne(Long projectId) {
        Project project = validateProject(projectId);
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        List<Long> memberIdList = projectMembers.stream()
                .map(projectMember -> projectMember.getMember().getId())
                .collect(Collectors.toList());
        return ProjectConverter.toProjectResponse(project, memberIdList);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectMemberResponse getAllProjectMembers(Long projectId) {
        Project project = validateProject(projectId);
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        return ProjectConverter.toProjectMemberResponse(projectMembers);
    }

    @Override
    @Transactional
    public void delete(Long projectId, Long memberId) {
        // 프로젝트 존재하는지 확인
        Project project = validateProject(projectId);

        // 현재 로그인한 사람이 프로젝트 담당자인지 확인
        checkIfIsManager(project.getManagerId(), memberId);

        // project members 삭제
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        projectMembers.stream()
                .map(ProjectMember::getId)
                .forEach(projectMemberRepository::deleteById);

        // project 삭제
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public void updateState(Long projectId, UpdateStateRequest request, Long memberId) {
        // 프로젝트 존재하는지 확인
        Project project = validateProject(projectId);

        // 현재 로그인한 사람이 프로젝트 담당자인지 확인
        checkIfIsManager(project.getManagerId(), memberId);

        ProjectStateName foundState = Arrays.stream(ProjectStateName.values())
                .filter(stateName -> stateName.toString().equals(request.projectState()))
                .findFirst()
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_STATE_NOT_FOUND));

        project.updateProjectState(foundState);
    }

    private void checkIfIsManager(Long projectManagerId, Long memberId) {
        if (!projectManagerId.equals(memberId)) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_PROJECT_MANAGER);
        }
    }

    private Project validateProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));
    }
}
