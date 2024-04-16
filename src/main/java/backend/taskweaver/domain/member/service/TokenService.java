package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.member.dto.CreateAccessTokenRequest;
import backend.taskweaver.domain.member.dto.CreateAccessTokenResponse;
import backend.taskweaver.global.converter.MemberConverter;
import backend.taskweaver.global.security.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;

    @Transactional
    public CreateAccessTokenResponse createNewAccessToken(CreateAccessTokenRequest request) throws JsonProcessingException {
        // 리프레시 토큰의 유효성 검사
        tokenProvider.validateRefreshToken(request.refreshToken(), request.oldAccessToken());
        // 새 엑세스 토큰 생성
        String accessToken = tokenProvider.recreateAccessToken(request.oldAccessToken());
        return MemberConverter.toCreateAccessTokenResponse(accessToken);
    }
}
