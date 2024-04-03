package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.task.dto.CommentRequest;
import backend.taskweaver.domain.task.dto.CommentResponse;
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
            return CommentConverter.toCommentResponse(comment, member);
        } 
        
        // 대댓글일 때
        else {
            Comment parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.COMMENT_NOT_FOUND));
            if (parentComment.getDepth() >= 1) {
                throw new BusinessExceptionHandler(ErrorCode.COMMENT_DEPTH_EXCEED);
            }
            Comment comment = commentRepository.save(CommentConverter.toComment(request, member, task, parentComment));
            parentComment.addChildrenComment(comment);
            return CommentConverter.toCommentResponse(comment, member);
        }
    }
}
