package backend.taskweaver.domain.task.repository;

import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
