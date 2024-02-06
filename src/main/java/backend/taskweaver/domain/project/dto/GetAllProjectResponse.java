package backend.taskweaver.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로젝트 전체 조회 응답 DTO")
public record GetAllProjectResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "oo과목 중간 대체 프로젝트")
        String projectName
) {
}
