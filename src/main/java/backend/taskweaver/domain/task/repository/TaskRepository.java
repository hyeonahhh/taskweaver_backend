package backend.taskweaver.domain.team.repository;

import backend.taskweaver.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
