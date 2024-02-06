package backend.taskweaver.domain.team.repository;

import backend.taskweaver.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}