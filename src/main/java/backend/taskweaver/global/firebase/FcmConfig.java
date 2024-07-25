package backend.taskweaver.global.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Configuration
public class FcmConfig {

    @Value("${firebase.path}")
    private String firebasePath;

    @PostConstruct // 서버 실행하면 다른 호출 없이도 실행한다: 이를 통해 서버 작동 시, Firebase 어플리케이션을 딱 한번만 초기화해준다.
    public void fcmInit() throws IOException {

        // 발급받은 FCM 키와 FCM 사용 범위를 통해 GoogleCredentials을 생성해준다.
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(this.firebasePath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // GoogleCredentials를 통해 FirebaseOptions을 생성해준다. 그리고 이 옵션 객체를 통해 FirebaseApp를 생성하고 FirebaseMessaging 객체를 생성한다.
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance(app);
    }
}
