package backend.taskweaver.global.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FcmService {

    public void sendMessage(List<String> targetToken, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle("TaskWeaver")
                .setBody(body)
                .build();

        List<Message> messages = targetToken.stream()
                .map(token -> Message.builder()
                        .setToken(token)
                        .setNotification(notification)
                        .build())
                .collect(Collectors.toList());

        FirebaseMessaging.getInstance().sendAll(messages);// 비동기로 보내는 걸로 바꾸기
    }

//    private final ObjectMapper objectMapper;
//    private final String API_URL = "https://fcm.googleapis.com/v1/projects/taskweaver-front/messages:send";
//
//    // 메세지 전송
//    public void sendMessageTo(String targetToken, String body) throws IOException {
//        String message = makeMessage(targetToken, body);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message,
//                MediaType.get("application/json; charset=utf-8"));
//
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        System.out.println(response.body().string());
//    }
//
//    // 메세지 생성
//    public String makeMessage(String targetToken, String body) throws JsonProcessingException {
//        FcmMessage.Notification notification = FcmMessage.Notification.builder()
//                .body(body)
//                .build();
//        FcmMessage.Message message = FcmMessage.Message.builder()
//                .token(targetToken)
//                .notification(notification)
//                .build();
//        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(message)
//                .validateOnly(false)
//                .build();
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//
//    // 토큰 발급: 서버에서 FCM API를 호출하여 메시지를 보낸다. 이때 서버의 신원을 인증하는 데 사용되는 토큰. . 여기서 토큰은 디바이스 토큰이 아니다.
//    public String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase_service_key.json";
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
}


