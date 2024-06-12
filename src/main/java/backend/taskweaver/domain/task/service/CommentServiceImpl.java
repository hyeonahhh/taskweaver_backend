package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
import backend.taskweaver.domain.task.dto.UpdateCommentRequest;
import backend.taskweaver.domain.task.entity.Comment;
import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.repository.CommentRepository;
import backend.taskweaver.domain.task.repository.TaskRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.CommentConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public CommentResponse create(CommentRequest request, Long taskId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_NOT_FOUND));

        // 최상위 댓글일 때
        if (request.getParentCommentId() == null) {
            Comment comment = commentRepository.save(CommentConverter.toComment(request, member, task));
            return CommentConverter.toCommentResponse(comment);
        }

        // 대댓글일 때
        Comment parentComment = commentRepository.findById(request.getParentCommentId())
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.COMMENT_NOT_FOUND));
        if (parentComment.getDepth() >= 1) {
            throw new BusinessExceptionHandler(ErrorCode.COMMENT_DEPTH_EXCEED);
        }
        Comment comment = commentRepository.save(CommentConverter.toComment(request, member, task, parentComment));
        parentComment.addChildrenComment(comment);
        return CommentConverter.toCommentResponse(comment);
}

    @Transactional
    @Override
    public void update(UpdateCommentRequest request, Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.COMMENT_NOT_FOUND));
        if(!comment.getMember().getId().equals(memberId)) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_COMMENT_WRITER);
        }
        comment.updateComment(request);
    }

    @Override
    public List<CommentResponse> get(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_NOT_FOUND));
        List<Comment> comments = commentRepository.findCommentByTaskId(taskId);
        return this.buildCommentHierarchy(comments);
    }

    private List<CommentResponse> buildCommentHierarchy(List<Comment> comments) {
        List<CommentResponse> commentResponseList = new ArrayList<>();
        Map<Long, CommentResponse> commentMap = new HashMap<>();
        comments.forEach(comment -> {
            CommentResponse commentResponse = CommentConverter.toCommentResponse(comment);
            commentMap.put(commentResponse.getCommentId(), commentResponse);
            // 대댓글이면
            if (commentResponse.getParentCommentId() != null) {
                // 부모 댓글의 response 찾기 -> response 내의 childrens 리스트에 자신의 response 넣기
                CommentResponse parentResponse = commentMap.get(commentResponse.getParentCommentId());
                parentResponse.getChildrens().add(commentResponse);
            // 최상위 댓글이면
            } else {
                commentResponseList.add(commentResponse);
            }
        });
        return commentResponseList;
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.COMMENT_NOT_FOUND));
        int depth = comment.getDepth();
        List<Comment> childrenComment = comment.getChildrenComment();

        // 대댓글이거나, 최상위 댓글이면서 대댓글이 없을 때
        if(depth == 1 || (depth == 0 && childrenComment.isEmpty())) {
            commentRepository.delete(comment);

        // 최상위 댓글이면서 대댓글이 있을 때
        } else {
            comment.deleteSoftly();
        }
    }
}
