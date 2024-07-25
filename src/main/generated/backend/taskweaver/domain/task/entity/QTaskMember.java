package backend.taskweaver.domain.task.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskMember is a Querydsl query type for TaskMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaskMember extends EntityPathBase<TaskMember> {

    private static final long serialVersionUID = -652127508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaskMember taskMember = new QTaskMember("taskMember");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final backend.taskweaver.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public final QTask task;

    public QTaskMember(String variable) {
        this(TaskMember.class, forVariable(variable), INITS);
    }

    public QTaskMember(Path<? extends TaskMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaskMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaskMember(PathMetadata metadata, PathInits inits) {
        this(TaskMember.class, metadata, inits);
    }

    public QTaskMember(Class<? extends TaskMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new backend.taskweaver.domain.member.entity.QMember(forProperty("member")) : null;
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

