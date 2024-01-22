package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        @NotNull
        @Email
        String email,
        @Schema(description = "회원 비밀번호", example = "1234")
        @NotNull
        String password,
        @Schema(description = "회원 닉네임", example = "코난")
        @NotNull
        String nickname,

        @Schema(description = "이미지 저장 주소", example = "domain 주소")
        String imageUrl

) {
}