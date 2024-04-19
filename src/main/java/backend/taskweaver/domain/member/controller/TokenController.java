package backend.taskweaver.domain.member.controller;

import backend.taskweaver.domain.member.dto.CreateAccessTokenRequest;
import backend.taskweaver.domain.member.service.TokenService;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/user/token")
    @Operation(summary = "엑세스 토큰 재발급 api", description = "엑세스 토큰이 만료시에 리프레시 토큰으로 새로운 엑세스 토큰 만들어주는 api입니다.")
    public ResponseEntity<ApiResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) throws JsonProcessingException {
        ApiResponse ar = ApiResponse.builder()
                .result(tokenService.createNewAccessToken(request))
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(ar, HttpStatus.CREATED);
    }
}
