package backend.taskweaver.domain.notification.service;

import backend.taskweaver.domain.notification.dto.NotificationResponse;
import backend.taskweaver.domain.notification.entity.NotificationMember;
import backend.taskweaver.domain.notification.repository.NotificationMemberRepository;
import backend.taskweaver.global.converter.NotificationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationMemberRepository notificationMemberRepository;

    public List<NotificationResponse.AllNotificationInfo> getAllNotificationsForUser(Long memberId) {
        // 로그인한 사용자의 모든 알림 조회
        List<NotificationMember> notificationMembers = notificationMemberRepository.findByMemberId(memberId);

        // 조회된 알림을 NotificationResponse.AllNotificationInfo 객체로 변환
        return notificationMembers.stream()
                .map(notificationMember -> NotificationConverter.toGetAllNotificationResponse(notificationMember.getNotification(), notificationMember))
                .collect(Collectors.toList());
    }
}
