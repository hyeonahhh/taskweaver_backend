package backend.taskweaver.domain.member.repository;

import backend.taskweaver.domain.member.entity.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByMemberIdAndReissueCountLessThan(Long id, Long count);

}
