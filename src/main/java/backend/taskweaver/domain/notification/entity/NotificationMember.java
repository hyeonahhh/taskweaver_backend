package backend.taskweaver.domain.notification.entity;
import backend.taskweaver.domain.notification.entity.enums.isRead;
import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@Setter
@Builder

@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_member_id")
    private Long id;

    @Column(nullable = false, name="is_read")
    @Enumerated(EnumType.STRING)
    private isRead isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noti_id")
    private Notification noti_id;
}

