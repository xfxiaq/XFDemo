package com.xfhy.common.model;


import static com.mysema.query.types.PathMetadataFactory.*;
import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QBaseModel is a Querydsl query type for BaseModel
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QBaseModel extends EntityPathBase<BaseModel> {

    private static final long serialVersionUID = 1023183762;

    public static final QBaseModel baseModel = new QBaseModel("baseModel");

    public final DateTimePath<java.util.Date> creationTime = createDateTime("creationTime", java.util.Date.class);

    public final StringPath creatorId = createString("creatorId");

    public final StringPath creatorTenantId = createString("creatorTenantId");

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> modificationTime = createDateTime("modificationTime", java.util.Date.class);

    public final StringPath modifierId = createString("modifierId");

    public final StringPath modifierTenantId = createString("modifierTenantId");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QBaseModel(String variable) {
        super(BaseModel.class, forVariable(variable));
    }

    public QBaseModel(Path<? extends BaseModel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseModel(PathMetadata<?> metadata) {
        super(BaseModel.class, metadata);
    }

}

