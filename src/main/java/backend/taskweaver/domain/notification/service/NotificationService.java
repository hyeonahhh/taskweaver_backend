package backend.taskweaver.domain.notification.service;

import backend.taskweaver.domain.notification.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    public List<NotificationResponse.AllNotificationInfo> getAllNotificationsForUser(Long memberId);
}
