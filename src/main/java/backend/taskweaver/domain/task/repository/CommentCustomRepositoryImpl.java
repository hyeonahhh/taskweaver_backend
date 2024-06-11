package backend.taskweaver.domain.task.repository;

import backend.taskweaver.domain.task.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static backend.taskweaver.domain.task.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findCommentByTaskId(Long taskId) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.parentComment)
                .fetchJoin()
                .where(comment.task.id.eq(taskId))
                .orderBy(comment.parentComment.id.asc().nullsFirst(), comment.createdAt.asc())
                .fetch();
    }
}
