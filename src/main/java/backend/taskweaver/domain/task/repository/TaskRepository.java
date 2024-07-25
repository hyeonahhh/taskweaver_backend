package backend.taskweaver.domain.task.repository;

import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.children " +
            "JOIN t.project p " +
            "JOIN p.team tm " +
            "WHERE tm.id = :teamId AND p.id = :projectId")
    List<Task> findAllTasksByTeamAndProject(@Param("teamId") Long teamId,
                                            @Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t WHERE t.endDate = :endDate")
    List<Task> findAllByEndDate(@Param("endDate") Date endDate);
}
