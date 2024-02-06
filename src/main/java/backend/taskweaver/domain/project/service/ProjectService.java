package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.GetAllProjectResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.team.entity.TeamMember;

import java.util.List;

public interface ProjectService{
    ProjectResponse createProject(ProjectRequest request, Long teamId);
    void createProjectMember(Project project, Long managerId);
    void checkIfTeamIdIsSame(TeamMember manager, Long teamId);
    List<GetAllProjectResponse> getAll(Long teamId);
}
