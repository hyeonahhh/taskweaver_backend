package backend.taskweaver.domain.project.dto;

import backend.taskweaver.domain.project.entity.enums.ProjectStateName;

public record ProjectResponse(
     Long projectId,
     String name,
     String description,
     Long managerId,
     ProjectStateName projectState
) {
}
