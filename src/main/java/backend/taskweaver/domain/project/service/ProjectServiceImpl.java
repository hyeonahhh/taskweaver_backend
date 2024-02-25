package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.dto.GetAllProjectResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.dto.UpdateStateRequest;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.project.entity.enums.ProjectRole;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.project.repository.ProjectMemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.ProjectConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        createProjectMember(project, request.managerId());

        return ProjectConverter.toProjectResponse(project, state);
    }

    @Override
    @Transactional
    public void createProjectMember(Project project, Long managerId) {
        // 매니저 id가 존재하는지 확인
        Member member = memberRepository.findById(managerId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 해당 매니저가 해당 팀에 존재하는지 확인
        Team team = project.getTeam();
        teamMemberRepository.findByTeamAndMember(team, member)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.BELONG_TO_WRONG_TEAM_ERROR));

        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(team);
        teamMembers.forEach(teamMember -> {
            Member foundMember = teamMember.getMember();
            if (foundMember.getId().equals(managerId)) {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, foundMember, ProjectRole.MANAGER);
                projectMemberRepository.save(projectMember);
                project.setManagerId(managerId);
            } else {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, foundMember, ProjectRole.NON_MANAGER);
                projectMemberRepository.save(projectMember);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAllProjectResponse> getAll(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        List<Project> projects = projectRepository.findAllByTeam(team);

        return projects.stream()
                .map(ProjectConverter::toGetAllProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getOne(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));
        return ProjectConverter.toProjectResponse(project, project.getProjectState());
    }

    @Override
    @Transactional
    public void updateState(Long projectId, UpdateStateRequest request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));

        // 지금 로그인한 사용자가 매니저면 프로젝트를 삭제한다.
        if (project.getManagerId().equals(memberId)) {
            ProjectStateName foundState = Arrays.stream(ProjectStateName.values())
                    .filter(stateName -> stateName.toString().equals(request.projectState()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_STATE_NOT_FOUND));

            ProjectState projectState = project.getProjectState();
            projectState.changeProjectState(foundState);
        } // 지금 로그인한 사용자가 매니저가 아니면 에러를 던진다.
        else {
            throw new BusinessExceptionHandler(ErrorCode.NOT_PROJECT_MANAGER);
        }
    }

    @Override
    @Transactional
    public void updateProject(Long projectId, ProjectRequest request, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));

        // 지금 로그인한 사용자가 매니저인지 확인한다. 아니면 에러를 던진다.
        checkIfIsManager(project.getManagerId(), memberId);

        // 이미 담당자인 사람을 선택하지 않았을 경우, 담당자와 프로젝트 상세 정보를 수정한다.
        if (!request.managerId().equals(project.getManagerId())) {
            changeRole(project.getManagerId(), projectId, ProjectRole.NON_MANAGER); // 기존 매니저의 권한을 없앤다.
            changeRole(request.managerId(), projectId, ProjectRole.MANAGER); // 새로운 매니저로 임명한다!
            project.updateProject(request); // project를 수정한다.
        } else {
            throw new BusinessExceptionHandler(ErrorCode.SAME_PROJECT_MANAGER);
        }
    }

    private void checkIfIsManager(Long projectManagerId, Long memberId) {
        if (!projectManagerId.equals(memberId)) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_PROJECT_MANAGER);
        }
    }

    private void changeRole(Long memberId, Long projectId, ProjectRole role) {
        ProjectMember projectMember = projectMemberRepository.findByMemberIdAndProjectId(memberId, projectId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PROJECT_MEMBER_NOT_FOUND));
        projectMember.changeRole(role);
    }
}
