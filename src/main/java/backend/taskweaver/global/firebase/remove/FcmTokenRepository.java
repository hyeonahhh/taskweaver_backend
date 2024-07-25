//package backend.taskweaver.global.firebase;
//
//import backend.taskweaver.global.redis.RedisService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@RequiredArgsConstructor
//public class FcmTokenRepository {
//
//    private final RedisService redisService;
//
//    public void saveToken(String email, String token) {
//        redisService.setValues(email, token);
//    }
//
//    public String getToken(String email) {
//        return redisService.getValues(email);
//    }
//
//    public void deleteToken(String email) {
//       redisService.deleteValues(email);
//    }
//}
