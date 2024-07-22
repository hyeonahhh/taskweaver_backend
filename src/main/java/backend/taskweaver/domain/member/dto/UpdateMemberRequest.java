package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "회원 정보 업데이트")
public record UpdateMemberRequest(

        @Schema(description = "회원 닉네임", example = "코난")
        @NotNull
        String nickname
) {
}