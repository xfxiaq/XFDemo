package com.xfhy.common.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QFunction is a Querydsl query type for Function
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFunction extends EntityPathBase<Function> {

    private static final long serialVersionUID = 641796062;

    public static final QFunction function = new QFunction("function");

    public final QBaseModel _super = new QBaseModel(this);

    public final StringPath authUrl = createString("authUrl");

    //inherited
    public final DateTimePath<java.util.Date> creationTime = _super.creationTime;

    //inherited
    public final StringPath creatorId = _super.creatorId;

    //inherited
    public final StringPath creatorTenantId = _super.creatorTenantId;

    public final BooleanPath enable = createBoolean("enable");

    public final StringPath functionName = createString("functionName");

    public final StringPath functionUrl = createString("functionUrl");

    public final StringPath ico = createString("ico");

    //inherited
    public final StringPath id = _super.id;

    public final BooleanPath isPreset = createBoolean("isPreset");

    //inherited
    public final DateTimePath<java.util.Date> modificationTime = _super.modificationTime;

    //inherited
    public final StringPath modifierId = _super.modifierId;

    //inherited
    public final StringPath modifierTenantId = _super.modifierTenantId;

    public final StringPath parentId = createString("parentId");

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class);

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public final StringPath urlRouter = createString("urlRouter");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QFunction(String variable) {
        super(Function.class, forVariable(variable));
    }

    public QFunction(Path<? extends Function> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFunction(PathMetadata<?> metadata) {
        super(Function.class, metadata);
    }

}

