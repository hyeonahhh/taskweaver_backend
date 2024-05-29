package backend.taskweaver.domain.notification.service;

import backend.taskweaver.domain.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class NotificationCleanupScheduler {
    private final NotificationService notificationService;

    @Autowired
    public NotificationCleanupScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (예시)
    public void scheduleOldNotificationCleanup() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(14);
        notificationService.deleteOldNotifications(cutoffDate);
    }
}

