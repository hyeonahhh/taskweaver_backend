package backend.taskweaver.domain.task.dto;


import backend.taskweaver.domain.task.entity.enums.TaskStateName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class TaskRequest {

    @Getter
    public static class taskCreate {
        @Schema(description = "태스크 제목", example = "Task Title")
        String title;

        @Schema(description = "태스크 내용", example = "Task Content")
        String content;

        @Schema(description = "태스크 시작 날짜", example = "2024-01-01")
        String startDate;

        @Schema(description = "태스크 종료 날짜", example = "2024-12-31")
        String endDate;

        @Schema(description = "태스크 참여자", example = "[1, 2, 3]")
        List<Long> members;

        @Schema(description = "태스크 색깔", example = "#FFFFFF")
        String color;

    }
}
