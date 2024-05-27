package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.dto.ProjectMemberResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.ProjectMember;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.team.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {

    public static Project toProject(ProjectRequest request, Team team) {
        return Project.builder()
                .name(request.name())
                .description(request.description())
                .team(team)
                .projectStateName(ProjectStateName.BEFORE)
                .build();
    }

    public static ProjectResponse toProjectResponse(Project project, List<Long> memberIdList) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getManagerId(),
                memberIdList,
                project.getProjectStateName(),
                project.getCreatedAt()
        );
    }

    public static ProjectMemberResponse toProjectMemberResponse(List<ProjectMember> projectMembers) {
        List<ProjectMemberResponse.MemberList> memberList = projectMembers.stream()
                .map(projectMember -> new ProjectMemberResponse.MemberList(
                        projectMember.getMember().getId(),
                        projectMember.getMember().getImageUrl(),
                        projectMember.getMember().getNickname()))
                .collect(Collectors.toList());
        return new ProjectMemberResponse(memberList);
    }

    public static ProjectMember toProjectMember(Project project, Member member) {
        return ProjectMember.builder()
                .member(member)
                .project(project)
                .build();
    }
}
