package backend.taskweaver.domain.team.repository;

import backend.taskweaver.domain.team.entity.TeamMemberState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberStateRepository extends JpaRepository<TeamMemberState, Long> {
    Optional<TeamMemberState> findByTeamIdAndMemberId(Long teamId, Long userId);
}