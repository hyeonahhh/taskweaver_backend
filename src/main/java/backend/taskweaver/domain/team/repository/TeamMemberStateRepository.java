package backend.taskweaver.domain.team.repository;

import backend.taskweaver.domain.team.entity.TeamMemberState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberStateRepository extends JpaRepository<TeamMemberState, Long> {
}