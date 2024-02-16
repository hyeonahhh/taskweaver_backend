package backend.taskweaver.domain.task.repository;


import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.entity.TaskMember;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMemberRepository extends JpaRepository<TaskMember, Long> {
    List<TaskMember> findAllByTask(Task task);
}
