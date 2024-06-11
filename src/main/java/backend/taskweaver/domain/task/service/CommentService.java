package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse create(CommentRequest request, Long taskId, Long memberId);
    void update(UpdateCommentRequest request, Long commentId, Long memberId);
    List<CommentResponse> get(Long taskId);
}
