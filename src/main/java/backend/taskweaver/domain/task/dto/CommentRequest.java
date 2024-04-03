package backend.taskweaver.domain.task.dto;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "댓글 등록 요청 DTO")
@Getter
public class CommentRequest {
        @NotBlank
        @Schema(description = "댓글 내용", example = "안녕하세요. 댓글입니다.")
        private String content;

        @Schema(description = "부모 댓글 ID", example = "1")
        @Nullable
        private Long parentCommentId;
}
