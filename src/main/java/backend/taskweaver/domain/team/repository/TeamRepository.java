package backend.taskweaver.domain.team.repository;

import backend.taskweaver.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Object> findByIdAndTeamLeader(Long teamId, Long user);
}
