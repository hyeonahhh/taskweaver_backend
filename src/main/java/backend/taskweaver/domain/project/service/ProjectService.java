package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectMemberResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.dto.UpdateStateRequest;

import java.io.IOException;
import java.util.List;

public interface ProjectService{
    ProjectResponse createProject(ProjectRequest request, Long teamId) throws IOException;
    List<ProjectResponse> getAll(Long teamId);
    ProjectResponse getOne(Long projectId);
    ProjectMemberResponse getAllProjectMembers(Long projectId);
    void updateState(Long projectId, UpdateStateRequest request, Long memberId);
    void updateProject(Long projectId, ProjectRequest request, Long memberId) throws IOException;
    void delete(Long projectId, Long memberId);
}
