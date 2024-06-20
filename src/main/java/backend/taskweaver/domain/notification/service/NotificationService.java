package backend.taskweaver.domain.notification.service;

import backend.taskweaver.domain.notification.dto.NotificationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    public List<NotificationResponse.AllNotificationInfo> getAllNotificationsForUser(Long memberId);
    public void deleteOldNotifications(LocalDateTime cutoffDate);
}
