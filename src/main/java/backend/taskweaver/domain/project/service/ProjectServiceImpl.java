package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.dto.ProjectMemberResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.dto.UpdateStateRequest;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.project.repository.ProjectMemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.project.repository.ProjectStateRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.ProjectConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectStateRepository projectStateRepository;

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, Long teamId) {
        // project state 저장
        ProjectState state = ProjectConverter.toProjectState(ProjectStateName.BEFORE);

        // project 저장
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        Project project = ProjectConverter.toProject(request, team, state);
        projectRepository.save(project);

        // project member 저장
        createProjectMember(project, request);

        return ProjectConverter.toProjectResponse(project, request.memberIdList());
    }

    @Override
    @Transactional
    public void createProjectMember(Project project, ProjectRequest request) {
        List<ProjectMember> projectMembers = new ArrayList<>();
        request.memberIdList().forEach(memberId -> {
            // 해당 회원이 존재하는지 확인
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

            // 해당 회원이 해당 팀에 존재하는지 확인
            teamMemberRepository.findByTeamAndMember(project.getTeam(), member)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_BELONG_TO_TEAM));

            ProjectMember projectMember = ProjectConverter.toProjectMember(project, member);
            projectMembers.add(projectMember);

            // 매니저면 매니저 ID 설정
            if (memberId.equals(request.managerId())) {
                project.setManagerId(request.managerId());
            }
        });
        projectMemberRepository.saveAll(projectMembers);
    }

    @Override
    @Transactional
    public void updateProject(Long projectId, ProjectRequest request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));

        // 지금 로그인한 사용자가 매니저인지 확인하고 아니면 에러를 던진다.
        checkIfIsManager(project.getManagerId(), memberId);

        project.updateProject(request);
        projectMemberRepository.deleteAllByProject(project);
        createProjectMember(project, request);
    }

    @Override
    public List<ProjectResponse> getAll(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        List<Project> projects = projectRepository.findAllByTeam(team);
        return projects.stream()
                .map(project -> ProjectConverter.toProjectResponse(project, null))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getOne(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        List<Long> memberIdList  = projectMembers.stream()
                .map(projectMember -> projectMember.getMember().getId())
                .collect(Collectors.toList());
        return ProjectConverter.toProjectResponse(project, memberIdList);
    }

    @Override
    @Transactional
    public ProjectMemberResponse getAllProjectMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        return ProjectConverter.toProjectMemberResponse(projectMembers);
    }

    @Override
    @Transactional
    public void delete(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));

        // 지금 로그인한 사용자가 매니저인지 확인하고 아니면 에러를 던진다.
        checkIfIsManager(project.getManagerId(), memberId);

        // project members 삭제
        List<ProjectMember> projectMembers = projectMemberRepository.findByProject(project);
        projectMembers.stream()
                .map(ProjectMember::getId)
                .forEach(projectMemberRepository::deleteById);

        // project state 삭제
        projectStateRepository.deleteById(project.getProjectState().getId());

        // project 삭제
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public void updateState(Long projectId, UpdateStateRequest request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));

        // 지금 로그인한 사용자가 매니저인지 확인하고 아니면 에러를 던진다.
        checkIfIsManager(project.getManagerId(), memberId);

        ProjectStateName foundState = Arrays.stream(ProjectStateName.values())
                .filter(stateName -> stateName.toString().equals(request.projectState()))
                .findFirst()
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_STATE_NOT_FOUND));

        ProjectState projectState = project.getProjectState();
        projectState.changeProjectState(foundState);
    }


    private void checkIfIsManager(Long projectManagerId, Long memberId) {
        if (!projectManagerId.equals(memberId)) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_PROJECT_MANAGER);
        }
    }
}
