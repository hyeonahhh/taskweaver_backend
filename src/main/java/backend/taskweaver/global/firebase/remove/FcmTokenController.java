//package backend.taskweaver.global.firebase;
//
//import backend.taskweaver.domain.member.dto.DeviceTokenRequest;
//import backend.taskweaver.global.code.ApiResponse;
//import backend.taskweaver.global.code.SuccessCode;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1")
//@Tag(name = "Firebase 관련 api")
//public class FcmTokenController {
//
//    private final FcmTokenService fcmTokenService;
//
//    @PostMapping("/user/deviceToken")
//    @Operation(summary = "디바이스 토큰 전달 api", description = "푸시 알림을 위해 디바이스 토큰을 전달하는 api입니다.")
//    public ResponseEntity<ApiResponse> saveDeviceToken(@RequestBody @Valid DeviceTokenRequest request,
//                                                       @AuthenticationPrincipal User user) {
//        fcmTokenService.save(request, Long.parseLong(user.getUsername()));
//        ApiResponse apiResponse = ApiResponse.builder()
//                .result(null)
//                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
//                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(apiResponse);
//    }
//}
