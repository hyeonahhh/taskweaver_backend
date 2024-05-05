package backend.taskweaver.domain.notification.entity;


import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.notification.entity.enums.NotificationType;
import backend.taskweaver.domain.team.entity.TeamMember;
import backend.taskweaver.domain.team.entity.enums.TeamRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder

@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long id;

    private String sender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;
}
