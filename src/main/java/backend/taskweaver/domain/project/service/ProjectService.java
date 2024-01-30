package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.ProjectState;

public interface ProjectService{
    ProjectState createProjectStateOnProgress();
    ProjectResponse createProject(ProjectRequest request, Long teamId);
}
