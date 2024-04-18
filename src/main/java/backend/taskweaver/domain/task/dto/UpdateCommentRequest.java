package backend.taskweaver.domain.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "댓글 수정 요청 DTO")
@Getter
public class UpdateCommentRequest {
    @NotBlank
    @Schema(description = "댓글 내용", example = "안녕하세요. 댓글입니다.")
    private String content;
}
