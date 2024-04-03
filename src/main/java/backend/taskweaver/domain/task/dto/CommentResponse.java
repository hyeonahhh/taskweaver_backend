package backend.taskweaver.domain.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 등록 응답 DTO")
@Getter
@AllArgsConstructor
public class CommentResponse {
    private CommentResponse.Comment comment;
    private CommentResponse.Member member;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Comment {
        Long commentId;
        String content;
        String createdAt;
        int depth;
        Long parentCommentId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Member {
        String nickname;
        String imageUrl;
    }
}

