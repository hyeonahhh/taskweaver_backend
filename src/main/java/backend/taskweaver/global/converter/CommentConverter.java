package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.entity.Comment;
import backend.taskweaver.domain.task.entity.Task;

import java.util.ArrayList;

public class CommentConverter {

    public static Comment toComment(CommentRequest request, Member member, Task task) {
        return Comment.parentBuilder()
                .content(request.getContent())
                .depth(0)
                .member(member)
                .task(task)
                .parentBuild();
    }

    public static Comment toComment(CommentRequest request, Member member, Task task, Comment parentComment) {
        return Comment.childBuilder()
                .content(request.getContent())
                .depth(1)
                .parentComment(parentComment)
                .member(member)
                .task(task)
                .childBuild();
    }


    public static CommentResponse toCommentResponse(Comment comment) {
        Long parentCommentId = comment.getParentComment() != null ? comment.getParentComment().getId() : null;
        return new CommentResponse(
                comment.getId(),
                parentCommentId,
                comment.getContent(),
                comment.getCreatedAt().toString(),
                comment.getMember().getId(),
                comment.getMember().getNickname(),
                comment.getMember().getImageUrl(),
                new ArrayList<>()
        );
    }
}
