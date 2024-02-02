package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;

public interface ProjectService{
    ProjectState createProjectStateOnProgress();
    ProjectResponse createProject(ProjectRequest request, Long teamId);
    void createProjectMember(Project project, Long managerId);
    void checkIfTeamIdIsSame(TeamMember manager, Long teamId);
}
