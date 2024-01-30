package backend.taskweaver.global.converter;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.ProjectState;
import backend.taskweaver.domain.project.entity.enums.ProjectRole;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;

public class ProjectConverter {

    public static Project toProject(ProjectRequest request, Team team, ProjectState state) {
        return Project.builder()
                .name(request.name())
                .description(request.description())
                .team(team)
                .projectState(state)
                .build();
    }

    public static ProjectState toProjectState() {
        return  ProjectState.builder()
                .stateName(ProjectStateName.ON_PROGRESS)
                .build();
    }

    public static ProjectResponse toProjectResponse(Project project, ProjectState state, Long managerId) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                managerId,
                state.getStateName()
        );
    }

    public static ProjectMember toProjectMember(Project project, TeamMember teamMember) {
        return ProjectMember.builder()
                .role(ProjectRole.NON_MANAGER)
                .member(teamMember.getMember())
                .project(project)
                .build();
    }
}
