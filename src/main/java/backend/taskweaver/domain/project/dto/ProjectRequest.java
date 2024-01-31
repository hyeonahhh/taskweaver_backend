package backend.taskweaver.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "프로젝트 요청 DTO")
public record ProjectRequest(
        @NotNull
        @Schema(description = "프로젝트 제목", example = "oo과목 중간 대체 프로젝트")
        String name,

        @NotNull
        @Schema(description = "프로젝트 설명", example = "이 프로젝트는 oo과목을 위한 프로젝트입니다.")
        String description,

        @NotNull
        @Schema(description = "프로젝트 담당자 ID (팀 멤버 ID)", example = "1")
        Long managerId
) {
}
