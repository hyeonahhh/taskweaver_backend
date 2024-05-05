package backend.taskweaver.domain.notification.repository;

import backend.taskweaver.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
