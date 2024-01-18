package backend.taskweaver.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // 토큰 생성 메서드
    public String createToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.ES512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getExpirationHours(), ChronoUnit.HOURS)))
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
}
