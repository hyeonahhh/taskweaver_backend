package backend.taskweaver.domain.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamMemberState is a Querydsl query type for TeamMemberState
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamMemberState extends EntityPathBase<TeamMemberState> {

    private static final long serialVersionUID = -1106742955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamMemberState teamMemberState = new QTeamMemberState("teamMemberState");

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

    public final EnumPath<backend.taskweaver.domain.team.entity.enums.InviteState> state = createEnum("state", backend.taskweaver.domain.team.entity.enums.InviteState.class);

    public final QTeam team;

    public QTeamMemberState(String variable) {
        this(TeamMemberState.class, forVariable(variable), INITS);
    }

    public QTeamMemberState(Path<? extends TeamMemberState> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamMemberState(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamMemberState(PathMetadata metadata, PathInits inits) {
        this(TeamMemberState.class, metadata, inits);
    }

    public QTeamMemberState(Class<? extends TeamMemberState> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new backend.taskweaver.domain.member.entity.QMember(forProperty("member")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
    }

}

