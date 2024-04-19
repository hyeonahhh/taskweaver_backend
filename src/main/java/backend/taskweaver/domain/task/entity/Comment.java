package backend.taskweaver.domain.task.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.task.dto.UpdateCommentRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "depth", nullable = false)
    private int depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childrenComment = new ArrayList<>();

    @Builder(builderMethodName = "parentBuilder", buildMethodName = "parentBuild")
    public Comment(String content, int depth, Member member, Task task) {
        this.content = content;
        this.depth = depth;
        this.member = member;
        this.task = task;
    }

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public Comment(String content, int depth, Member member, Task task, Comment parentComment) {
        this.content = content;
        this.depth = depth;
        this.member = member;
        this.task = task;
        this.parentComment = parentComment;
    }

    public void addChildrenComment(Comment comment) {
        this.childrenComment.add(comment);
    }

    public void updateComment(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}
