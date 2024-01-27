package backend.taskweaver.domain.project.entity;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.entity.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ProjectMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_role", nullable = false)
    private ProjectRole projectRole;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
