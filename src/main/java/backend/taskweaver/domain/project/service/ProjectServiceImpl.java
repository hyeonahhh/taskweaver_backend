package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.project.entity.enums.ProjectRole;
import backend.taskweaver.domain.project.repository.ProjectMemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.project.repository.ProjectStateRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;
import backend.taskweaver.domain.team.repository.TeamMemberRepository;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.converter.ProjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectStateRepository projectStateRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    @Transactional
    public ProjectState createProjectStateOnProgress() {
        ProjectState state = ProjectConverter.toProjectState();
        return projectStateRepository.save(state);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, Long teamId) {
        // project state 저장
        ProjectState state = createProjectStateOnProgress();

        // project 저장
        Team team = teamRepository.findById(teamId).get();
        Project project = ProjectConverter.toProject(request, team, state);
        projectRepository.save(project);

        // project member 저장
        Long managerId = request.managerId();
        createProjectMember(project, managerId);

        return ProjectConverter.toProjectResponse(project, state, managerId);
    }

    @Override
    @Transactional
    public void createProjectMember(Project project, Long managerId) {

        // 예외 처리
        checkIfManagerIdExist(managerId); // managerId가 아예 존재하지 않을 경우

        // 팀 member를 가져와 project member로 저장시키기
        Team team = project.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(team);

        teamMembers.forEach(teamMember -> {
            Long teamMemberId = teamMember.getId();
            if (teamMemberId.equals(managerId)) {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, teamMember, ProjectRole.MANAGER);
                projectMemberRepository.save(projectMember);
            } else {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, teamMember, ProjectRole.NON_MANAGER);
                projectMemberRepository.save(projectMember);
            }
        });
    }

    @Override
    public void checkIfManagerIdExist(Long managerId) {
        teamMemberRepository.findById(managerId)
                .orElseThrow(NoSuchElementException::new);
    }
}
