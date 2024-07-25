package backend.taskweaver.domain.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "댓글 등록 응답 DTO")
@Getter
@AllArgsConstructor
public class CommentResponse {
    Long commentId;
    Long parentCommentId;
    String content;
    String createdAt;
    String deletedAt;
    Long memberId;
    String nickname;
    String imageUrl;
    List<CommentResponse> childrens;
}

