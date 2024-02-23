package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.dto.GetAllProjectResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
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
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.BELONG_TO_WRONG_TEAM_ERROR));

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
                .orElseThrow(()-> new BusinessExceptionHandler(ErrorCode.TEAM_NOT_FOUND));
        List<Project> projects = projectRepository.findAllByTeam(team);

        return  projects.stream()
                .map(ProjectConverter::toGetAllProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getOne(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new BusinessExceptionHandler(ErrorCode.PROJECT_NOT_FOUND));
        return ProjectConverter.toProjectResponse(project, project.getProjectState());
    }
}
