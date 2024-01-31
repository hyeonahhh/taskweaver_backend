package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.team.entity.Team;

public interface ProjectService{
    ProjectState createProjectStateOnProgress();
    ProjectResponse createProject(ProjectRequest request, Long teamId);
    void createProjectMember(Project project, Long managerId);
    Team checkIfTeamExist(Long teamId);
    void checkIfManagerIdExist(Long managerId);
    void checkIfTeamIdIsSame(Long managerId, Long teamId);
}
