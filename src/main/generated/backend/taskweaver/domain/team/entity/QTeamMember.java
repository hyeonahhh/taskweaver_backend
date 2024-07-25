package backend.taskweaver.domain.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamMember is a Querydsl query type for TeamMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamMember extends EntityPathBase<TeamMember> {

    private static final long serialVersionUID = -897456996L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamMember teamMember = new QTeamMember("teamMember");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final backend.taskweaver.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<backend.taskweaver.domain.team.entity.enums.TeamRole> role = createEnum("role", backend.taskweaver.domain.team.entity.enums.TeamRole.class);

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public final QTeam team;

    public QTeamMember(String variable) {
        this(TeamMember.class, forVariable(variable), INITS);
    }

    public QTeamMember(Path<? extends TeamMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamMember(PathMetadata metadata, PathInits inits) {
        this(TeamMember.class, metadata, inits);
    }

    public QTeamMember(Class<? extends TeamMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new backend.taskweaver.domain.member.entity.QMember(forProperty("member")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
    }

}

