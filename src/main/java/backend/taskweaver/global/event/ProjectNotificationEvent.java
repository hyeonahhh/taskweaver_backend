package backend.taskweaver.global.event;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.entity.Project;
import java.util.List;

public record ProjectNotificationEvent(
        Project project,
        List<Member> members
) {
}
