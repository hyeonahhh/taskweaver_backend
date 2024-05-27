package backend.taskweaver.global.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FcmMessage {

    private boolean validateOnly;
    private Message message;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Notification {
        private String body;
    }
}
