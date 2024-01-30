package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.ProjectState;
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
        Team team = project.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAlByTeam(team);

        teamMembers.stream()
                .map(teamMember -> ProjectConverter.toProjectMember(project, teamMember))
                .forEach(projectMemberRepository::save);

        ProjectMember member = projectMemberRepository.findById(managerId).get();
        member.changeToManager();
    }
}
