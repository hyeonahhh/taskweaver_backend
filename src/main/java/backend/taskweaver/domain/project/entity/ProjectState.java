package backend.taskweaver.domain.project.entity;


import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "project_state")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class ProjectState extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_state_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_state_name", nullable = false)
    private ProjectStateName stateName;

    public void changeProjectState(ProjectStateName projectStateName) {
        this.stateName = projectStateName;
    }
}
