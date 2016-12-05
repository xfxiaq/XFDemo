package com.xfhy.common.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = -1049753732;

    public static final QRole role = new QRole("role");

    public final QBaseModel _super = new QBaseModel(this);

    //inherited
    public final DateTimePath<java.util.Date> creationTime = _super.creationTime;

    //inherited
    public final StringPath creatorId = _super.creatorId;

    //inherited
    public final StringPath creatorTenantId = _super.creatorTenantId;

    public final ListPath<Function, QFunction> functions = this.<Function, QFunction>createList("functions", Function.class, QFunction.class);

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> modificationTime = _super.modificationTime;

    //inherited
    public final StringPath modifierId = _super.modifierId;

    //inherited
    public final StringPath modifierTenantId = _super.modifierTenantId;

    public final StringPath roleCode = createString("roleCode");

    public final StringPath roleName = createString("roleName");

    public final ListPath<User, QUser> users = this.<User, QUser>createList("users", User.class, QUser.class);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata<?> metadata) {
        super(Role.class, metadata);
    }

}

