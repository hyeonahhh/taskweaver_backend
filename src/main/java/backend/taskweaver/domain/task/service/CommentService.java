package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.dto.UpdateCommentRequest;

public interface CommentService {
    CommentResponse create(CommentRequest request, Long taskId, Long memberId);
    void update(UpdateCommentRequest request, Long commentId, Long memberId);
}
