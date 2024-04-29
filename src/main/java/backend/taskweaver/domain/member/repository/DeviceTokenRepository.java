package backend.taskweaver.domain.member.repository;

import backend.taskweaver.domain.member.entity.DeviceToken;
import backend.taskweaver.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    List<DeviceToken> findAllByMember(Member member);
    List<DeviceToken> findAllByMemberIn(List<Member> members);
    Optional<DeviceToken> findByDeviceToken(String deviceToken);
}
