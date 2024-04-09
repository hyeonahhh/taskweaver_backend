package backend.taskweaver.domain.project.dto;

import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "프로젝트 응답 DTO")
public record ProjectResponse(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "oo과목 중간 대체 프로젝트")
        String name,

        @Schema(description = "프로젝트 설명", example = "이 프로젝트는 oo과목을 위한 프로젝트입니다.")
        String description,

        @Schema(description = "프로젝트 담당자 ID", example = "1")
        Long managerId,

        @Schema(description = "프로젝트 멤버들 ID", example = "[1, 2, 3, 4]")
        List<Long> memberId,

        @Schema(description = "프로젝트 상태", example = "ON_PROGRESS")
        ProjectStateName projectState,

        @Schema(description = "프로젝트 생성 날짜", example = "2024-04-04T11:04:54.1920")
        LocalDateTime createdAt
) {
}
