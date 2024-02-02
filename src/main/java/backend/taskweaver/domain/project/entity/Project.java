package backend.taskweaver.domain.project.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String name;

    @Column(name = "poject_description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_state_id")
    private ProjectState projectState;

    @Column(name = "manager_id")
    private Long managerId;

    @Builder
    public Project(String name, String description, Team team, ProjectState projectState) {
        this.name = name;
        this.description = description;
        this.team = team;
        this.projectState = projectState;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}
