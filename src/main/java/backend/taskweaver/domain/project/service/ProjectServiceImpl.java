package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.project.repository.ProjectStateRepository;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.global.converter.ProjectConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final ProjectStateRepository projectStateRepository;

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

        return ProjectConverter.toProjectResponse(project, state, request.managerId());
    }
}
