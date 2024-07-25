package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailResponse(
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        String email,
        @Schema(description = "회원 인증번호", example = "999999")
        String certificationNum
) {

}