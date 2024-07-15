package backend.taskweaver.domain.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotificationMember is a Querydsl query type for NotificationMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotificationMember extends EntityPathBase<NotificationMember> {

    private static final long serialVersionUID = 1241237944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotificationMember notificationMember = new QNotificationMember("notificationMember");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<backend.taskweaver.domain.notification.entity.enums.isRead> isRead = createEnum("isRead", backend.taskweaver.domain.notification.entity.enums.isRead.class);

    public final backend.taskweaver.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QNotification notification;

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public QNotificationMember(String variable) {
        this(NotificationMember.class, forVariable(variable), INITS);
    }

    public QNotificationMember(Path<? extends NotificationMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotificationMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotificationMember(PathMetadata metadata, PathInits inits) {
        this(NotificationMember.class, metadata, inits);
    }

    public QNotificationMember(Class<? extends NotificationMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new backend.taskweaver.domain.member.entity.QMember(forProperty("member")) : null;
        this.notification = inits.isInitialized("notification") ? new QNotification(forProperty("notification")) : null;
    }

}

