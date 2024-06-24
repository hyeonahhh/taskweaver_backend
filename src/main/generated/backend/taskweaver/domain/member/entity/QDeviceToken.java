package backend.taskweaver.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeviceToken is a Querydsl query type for DeviceToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeviceToken extends EntityPathBase<DeviceToken> {

    private static final long serialVersionUID = -75931647L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeviceToken deviceToken1 = new QDeviceToken("deviceToken1");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath deviceToken = createString("deviceToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public QDeviceToken(String variable) {
        this(DeviceToken.class, forVariable(variable), INITS);
    }

    public QDeviceToken(Path<? extends DeviceToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeviceToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeviceToken(PathMetadata metadata, PathInits inits) {
        this(DeviceToken.class, metadata, inits);
    }

    public QDeviceToken(Class<? extends DeviceToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

