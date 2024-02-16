package backend.taskweaver.domain.task.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TaskMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public TaskMember(Task task, Member member) {
        this.task = task;
        this.member = member;
    }
}
