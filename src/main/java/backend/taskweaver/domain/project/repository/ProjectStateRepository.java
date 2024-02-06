package backend.taskweaver.domain.project.repository;

import backend.taskweaver.domain.project.entity.ProjectState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStateRepository extends JpaRepository<ProjectState, Long> {
}
