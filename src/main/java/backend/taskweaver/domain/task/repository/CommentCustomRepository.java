package backend.taskweaver.domain.task.repository;

import backend.taskweaver.domain.task.entity.Comment;

import java.util.List;

public interface CommentCustomRepository {
    List<Comment> findCommentByTaskId(Long taskId);
}
