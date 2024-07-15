package backend.taskweaver.domain.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotification is a Querydsl query type for Notification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotification extends EntityPathBase<Notification> {

    private static final long serialVersionUID = -1084888706L;

    public static final QNotification notification = new QNotification("notification");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<NotificationMember, QNotificationMember> notificationMembers = this.<NotificationMember, QNotificationMember>createList("notificationMembers", NotificationMember.class, QNotificationMember.class, PathInits.DIRECT2);

    public final NumberPath<Long> relatedTypeId = createNumber("relatedTypeId", Long.class);

    public final StringPath sender = createString("sender");

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public final EnumPath<backend.taskweaver.domain.notification.entity.enums.NotificationType> type = createEnum("type", backend.taskweaver.domain.notification.entity.enums.NotificationType.class);

    public QNotification(String variable) {
        super(Notification.class, forVariable(variable));
    }

    public QNotification(Path<? extends Notification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotification(PathMetadata metadata) {
        super(Notification.class, metadata);
    }

}

