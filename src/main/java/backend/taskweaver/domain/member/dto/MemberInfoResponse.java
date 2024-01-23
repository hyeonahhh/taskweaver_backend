package backend.taskweaver.domain.member.dto;

import backend.taskweaver.domain.member.entity.enums.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
        @Schema(description = "회원 아이디", example = "1")
        Long id,
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        String email,
        @Schema(description = "회원 닉네임", example = "코난")
        String nickname,
        @Schema(description = "로그인 유형", example = "DEFAULT")
        LoginType type,
        @Schema(description = "이미지 저장 주소", example = "domain 주소")
        String imageUrl
) {
}
