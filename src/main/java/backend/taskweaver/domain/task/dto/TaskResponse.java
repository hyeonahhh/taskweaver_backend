package backend.taskweaver.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskInquiryResult {
        private Long id;
        private String title;
        private Long emojiId;
        private String endDate;
        private List<taskMemberResult> members;
        private List<childTaskInquiryResult> children;
        private taskStatusCountResult childTaskStatusCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class childTaskInquiryResult {
        private Long id;
        private String title;
        private Long emojiId;
        private String endDate;
        private List<taskMemberResult> members;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskStatusCountResult {
        private int before;
        private int onProgress;
        private int complete;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class todoInquiryResult {
        private String endDate;
        private Long teamId;
        private String teamName;
        private Long projectId;
        private String projectName;
        private List<todoTaskResult> tasks;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class todoTaskResult {
        private Long taskId;
        private String title;
        private String content;
    }

}
