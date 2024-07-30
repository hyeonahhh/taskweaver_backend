package backend.taskweaver.domain.task.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = -690525774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTask task = new QTask("task");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    public final ListPath<Task, QTask> children = this.<Task, QTask>createList("children", Task.class, QTask.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> emojiId = createNumber("emojiId", Long.class);

    public final DatePath<java.util.Date> endDate = createDate("endDate", java.util.Date.class);

    public final ListPath<backend.taskweaver.domain.files.entity.Files, backend.taskweaver.domain.files.entity.QFiles> files = this.<backend.taskweaver.domain.files.entity.Files, backend.taskweaver.domain.files.entity.QFiles>createList("files", backend.taskweaver.domain.files.entity.Files.class, backend.taskweaver.domain.files.entity.QFiles.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QTask parentTask;

    public final backend.taskweaver.domain.project.entity.QProject project;

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public final DatePath<java.util.Date> startDate = createDate("startDate", java.util.Date.class);

    public final ListPath<TaskMember, QTaskMember> taskMembers = this.<TaskMember, QTaskMember>createList("taskMembers", TaskMember.class, QTaskMember.class, PathInits.DIRECT2);

    public final EnumPath<backend.taskweaver.domain.task.entity.enums.TaskStateName> taskState = createEnum("taskState", backend.taskweaver.domain.task.entity.enums.TaskStateName.class);

    public final StringPath title = createString("title");

    public QTask(String variable) {
        this(Task.class, forVariable(variable), INITS);
    }

    public QTask(Path<? extends Task> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTask(PathMetadata metadata, PathInits inits) {
        this(Task.class, metadata, inits);
    }

    public QTask(Class<? extends Task> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentTask = inits.isInitialized("parentTask") ? new QTask(forProperty("parentTask"), inits.get("parentTask")) : null;
        this.project = inits.isInitialized("project") ? new backend.taskweaver.domain.project.entity.QProject(forProperty("project"), inits.get("project")) : null;
    }

}

