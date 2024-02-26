package backend.taskweaver.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "프로젝트 상태 변경 요청 DTO")
public record UpdateStateRequest(
        @NotNull
        @Schema(description = "변경하고 싶은 프로젝트 상태", example = "ON_PROGRESS")
        String projectState
) {
}
