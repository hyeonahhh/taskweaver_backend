package backend.taskweaver.domain.project.repository;

import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByTeam(Team team);
}
