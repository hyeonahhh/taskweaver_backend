package backend.taskweaver.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Schema(description = "프로젝트 요청 DTO")
@Getter
public class ProjectRequest{
        @NotBlank
        @Schema(description = "프로젝트 제목", example = "oo과목 중간 대체 프로젝트")
        String name;

        @NotBlank
        @Schema(description = "프로젝트 설명", example = "이 프로젝트는 oo과목을 위한 프로젝트입니다.")
        String description;

        @NotNull
        @Schema(description = "프로젝트 담당자 ID", example = "1")
        Long managerId;

        @NotNull
        @Schema(description = "프로젝트 멤버 ID", example = "[1, 2, 3]")
        List<Long> memberIdList;
}
