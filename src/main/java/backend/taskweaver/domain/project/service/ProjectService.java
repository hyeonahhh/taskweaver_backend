package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectMemberResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;

import java.util.List;

public interface ProjectService{
    ProjectResponse createProject(ProjectRequest request, Long teamId);
    List<ProjectResponse> getAll(Long teamId);
    ProjectResponse getOne(Long projectId);
    ProjectMemberResponse getAllProjectMembers(Long projectId);
    void updateProject(Long projectId, ProjectRequest request, Long memberId);
    // void delete(Long projectId, Long memberId);
    // void updateState(Long projectId, UpdateStateRequest request, Long memberId);
}
