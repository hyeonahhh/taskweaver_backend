package backend.taskweaver.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class TaskResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskCreateOrUpdateResult {
        private Long id;
        private String title;
        private String content;
        private String startDate;
        private String endDate;
        private List<taskMemberResult> taskMember;
        private List<fileResult> files;
        private Integer taskState;
        private Long parentTaskId;
        private Long emojiId;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskDeleteResult {
        private Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskMemberResult {
        private Long id;
        private String imageUrl;
        private String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class fileResult {
        private Long id;
        private String originalName;
        private String imageUrl;
        private Long taskId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskChangeStateResult {
        private Long id;
        private Integer taskState;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskUpdateResult {
        private Long id;
        private String title;
        private String content;
        private String endDate;
        private Long emojiId;
    }
}
