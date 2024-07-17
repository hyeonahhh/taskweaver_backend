package backend.taskweaver.domain.project.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE project SET deleted_at = NOW() WHERE project_id = ?")
@Where(clause = "deleted_at is null")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "project_state", nullable = false)
    private ProjectStateName projectStateName;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "manager_name")
    private String managerName;

    @Builder
    public Project(String name, String description, Team team, ProjectStateName projectStateName) {
        this.name = name;
        this.description = description;
        this.team = team;
        this.projectStateName = projectStateName;
    }

    public void setManager(Long managerId, String managerName) {
        this.managerId = managerId;
        this.managerName = managerName;
    }

    public void updateProject(ProjectRequest request) {
        this.description = request.getDescription();
        this.name = request.getName();
        this.managerId = request.getManagerId();
    }

    public void updateProjectState(ProjectStateName projectStateName) {
        this.projectStateName = projectStateName;
    }
}
