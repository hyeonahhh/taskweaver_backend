package backend.taskweaver.global.security;

import backend.taskweaver.domain.member.entity.MemberRefreshToken;
import backend.taskweaver.domain.member.repository.MemberRefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 토큰 생성 메서드
    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getExpirationMinutes(), ChronoUnit.HOURS)))
                .compact();
    }

    // 비밀키를 토대로 createToken()에서 토큰에 담은 Subject를 복호화하여 문자열 형태로 반환하는 메소드
    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 리프레시 토큰은 사용자와 관련된 정보를 전혀 담지 않을 것이기 때문에 subject는 따로 설정하지 않고 발급자와 발급시간, 만료시간만 설정한다.
    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getRefreshExpirationHours(), ChronoUnit.HOURS)))
                .compact();
    }

    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        long reissueLimit = jwtProperties.getReissueLimit();
        reissueLimit = jwtProperties.getRefreshExpirationHours()* 60 / jwtProperties.getExpirationMinutes();
        memberRefreshTokenRepository.findByMemberIdAndReissueCountLessThan(Long.valueOf(subject.split(":")[0]), reissueLimit)
                .ifPresentOrElse(
                        MemberRefreshToken::increaseReissueCount,
                        () -> { throw new ExpiredJwtException(null, null, "Refresh token expired."); }
                );
        return createAccessToken(subject);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String memberId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
        long reissueLimit = jwtProperties.getReissueLimit();
        reissueLimit = jwtProperties.getRefreshExpirationHours()* 60 / jwtProperties.getExpirationMinutes();
        memberRefreshTokenRepository.findByMemberIdAndReissueCountLessThan(Long.valueOf(memberId), reissueLimit)
                .filter(memberRefreshToken -> memberRefreshToken.validateRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
    }

    private Jws<Claims> validateAndParseToken(String token) {	// validateTokenAndGetSubject에서 따로 분리
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token);
    }

    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }
}
