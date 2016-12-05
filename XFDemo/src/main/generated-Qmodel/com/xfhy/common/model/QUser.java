package com.xfhy.common.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1049660719;

    public static final QUser user = new QUser("user");

    public final QBaseModel _super = new QBaseModel(this);

    public final StringPath avatarId = createString("avatarId");

    //inherited
    public final DateTimePath<java.util.Date> creationTime = _super.creationTime;

    //inherited
    public final StringPath creatorId = _super.creatorId;

    //inherited
    public final StringPath creatorTenantId = _super.creatorTenantId;

    public final BooleanPath enable = createBoolean("enable");

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> modificationTime = _super.modificationTime;

    //inherited
    public final StringPath modifierId = _super.modifierId;

    //inherited
    public final StringPath modifierTenantId = _super.modifierTenantId;

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class);

    public final StringPath tenantId = createString("tenantId");

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

