package backend.taskweaver.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projcet_state_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProjectStateMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_state_mapping_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_state_id")
    private ProjectState projectState;
}
