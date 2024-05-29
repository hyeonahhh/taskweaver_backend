package backend.taskweaver.global.converter;

import backend.taskweaver.domain.notification.dto.NotificationResponse;
import backend.taskweaver.domain.notification.entity.Notification;
import backend.taskweaver.domain.notification.entity.NotificationMember;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

public class NotificationConverter {

    public static NotificationResponse.AllNotificationInfo toGetAllNotificationResponse(Notification notification, NotificationMember notificationMember) {
        return new NotificationResponse.AllNotificationInfo(
                notification.getId(),
                notification.getSender(),
                notification.getContent(),
                notification.getType(),
                notification.getRelatedTypeId(),
                notificationMember.getIsRead().name(),
                notificationMember.getMember().getId()
        );
    }

}
