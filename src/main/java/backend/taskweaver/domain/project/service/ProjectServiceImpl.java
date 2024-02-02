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
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.ProjectConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
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
         /* project state 저장 */
        ProjectState state = createProjectStateOnProgress();

         /* project 저장 */
        Team team = teamRepository.findById(teamId).get();
        Project project = ProjectConverter.toProject(request, team, state);
        projectRepository.save(project);

         /* project member 저장 */
        Long managerId = request.managerId();
        TeamMember manager = teamMemberRepository.findById(managerId).get();
        checkIfTeamIdIsSame(manager, teamId); // 예외처리: 프론트에서 보내온 manager Id가 속해있는 team이 프론트에서 보내온 teamId의 team과 동일한지 확인
        createProjectMember(project, managerId);

        return ProjectConverter.toProjectResponse(project, state);
    }

    @Override
    @Transactional
    public void createProjectMember(Project project, Long managerId) {
        Team team = project.getTeam();
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeam(team);

        teamMembers.forEach(teamMember -> {
            Long teamMemberId = teamMember.getId();
            if (teamMemberId.equals(managerId)) {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, teamMember, ProjectRole.MANAGER);
                projectMemberRepository.save(projectMember);
                project.setManagerId(teamMemberId);
            } else {
                ProjectMember projectMember = ProjectConverter.toProjectMember(project, teamMember, ProjectRole.NON_MANAGER);
                projectMemberRepository.save(projectMember);
            }
        });
    }

    @Override
    public void checkIfTeamIdIsSame(TeamMember manager, Long teamId) {
        Long teamIdFromManager = manager.getTeam().getId();
        if(!teamIdFromManager.equals(teamId)) {
            throw new BusinessExceptionHandler(ErrorCode.BELONG_TO_WRONG_TEAM_ERROR);
        }
    }
}
