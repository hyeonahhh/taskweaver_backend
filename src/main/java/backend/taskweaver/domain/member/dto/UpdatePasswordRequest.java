package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "비밀번호 변경 요청 DTO")
public record UpdatePasswordRequest(
        @NotNull
        @Schema(description = "기존 비밀번호", example = "1234")
        String oldPassword,

        @NotNull
        @Schema(description = "새로 바꾸려는 비밀번호", example = "12345678")
        String newPassword
) {
}