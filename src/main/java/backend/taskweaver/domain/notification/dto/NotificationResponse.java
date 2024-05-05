package backend.taskweaver.domain.notification.dto;

import backend.taskweaver.domain.notification.entity.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllNotificationInfo {
        @Schema(description = "알림 id", example = "1")
        Long id;

        @Schema(description = "발신자", example = "김민지")
        String sender;

        @Schema(description = "내용", example = "김민지님, TaskWeaver 팀에 초대되었습니다.")
        private String content;

        @Schema(description = "알림 타입", example = "Team")
        NotificationType type;
    }
}
