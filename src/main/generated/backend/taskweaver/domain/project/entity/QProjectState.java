package backend.taskweaver.domain.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectState is a Querydsl query type for ProjectState
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectState extends EntityPathBase<ProjectState> {

    private static final long serialVersionUID = 1232943683L;

    public static final QProjectState projectState = new QProjectState("projectState");

    public final backend.taskweaver.domain.QBaseEntity _super = new backend.taskweaver.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final BooleanPath softDeleted = _super.softDeleted;

    public final EnumPath<backend.taskweaver.domain.project.entity.enums.ProjectStateName> stateName = createEnum("stateName", backend.taskweaver.domain.project.entity.enums.ProjectStateName.class);

    public QProjectState(String variable) {
        super(ProjectState.class, forVariable(variable));
    }

    public QProjectState(Path<? extends ProjectState> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectState(PathMetadata metadata) {
        super(ProjectState.class, metadata);
    }

}

