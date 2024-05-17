package backend.taskweaver.global.event;

import backend.taskweaver.domain.project.dto.ProjectNotificationMessage;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.global.firebase.FcmService;
import backend.taskweaver.global.redis.RedisService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectEventListener {
    private final FcmService fcmService;
    private final RedisService redisService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    @Async
    public void createProjectNotification(final ProjectNotificationEvent event) throws FirebaseMessagingException {
        Project project = event.project();
        ProjectNotificationMessage notificationMessage = new ProjectNotificationMessage(project.getTeam().getName(), project.getName(), "매니저명");
        List<String> tokenList = event.members().stream()
                .map(member -> redisService.getValues(member.getEmail()))
                .collect(Collectors.toList());
        fcmService.sendMessage(tokenList, notificationMessage.getCreateMessage());
        //fcmService.sendMessage(event.getDeviceToken(), event.getMessage());
    }
}
