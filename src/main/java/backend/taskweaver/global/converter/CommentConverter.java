package backend.taskweaver.global.converter;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.entity.Comment;
import backend.taskweaver.domain.task.entity.Task;

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

    public static CommentResponse toCommentResponse(Comment comment, Member member) {
        CommentResponse.Member memberResponse = new CommentResponse.Member(
                member.getId(),
                member.getNickname(),
                member.getImageUrl());

        Long parentCommentId = comment.getParentComment() != null ? comment.getParentComment().getId() : null;
        CommentResponse.Comment commentResponse = new CommentResponse.Comment(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt().toString(),
                comment.getDepth(),
                parentCommentId);

        return new CommentResponse(commentResponse, memberResponse);
    }
}
