package backend.taskweaver.domain.member.entity;

import backend.taskweaver.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeviceToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_token_id")
    private Long id;

    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public DeviceToken(String deviceToken, Member member) {
        this.deviceToken = deviceToken;
        this.member = member;
    }
}