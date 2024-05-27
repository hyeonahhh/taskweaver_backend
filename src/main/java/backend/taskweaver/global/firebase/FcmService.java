package backend.taskweaver.global.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FcmService {

    private final ObjectMapper objectMapper;
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/taskweaver-front/messages:send";

    // 메세지 전송
    public void sendMessageTo(String targetToken, String body) throws IOException {
        String message = makeMessage(targetToken, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());

    }

    // 메세지 생성
    public String makeMessage(String targetToken, String body) throws JsonProcessingException {
        FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .body(body)
                .build();
        FcmMessage.Message message = FcmMessage.Message.builder()
                .token(targetToken)
                .notification(notification)
                .build();
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(message)
                .validateOnly(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    // 토큰 발급: 서버에서 FCM API를 호출하여 메시지를 보낸다. 이때 서버의 신원을 인증하는 데 사용되는 토큰. . 여기서 토큰은 디바이스 토큰이 아니다.
    public String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase_service_key.json"; // todo: 이거 @Value로 바꾸기,  @Value("${firebase.sdk.path}")private String firebaseSdkPath;
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}


