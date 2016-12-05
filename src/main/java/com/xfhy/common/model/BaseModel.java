package com.xfhy.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.xfhy.common.utils.LoginUserUtils;


/**
 * 数据模型基类。实现基本的UUID、更新时间、创建时间信息。
 * 
 * @author zxl
 * @version 0.1
 */
@MappedSuperclass
public class BaseModel implements Serializable, IdVersion {
    /** 序列号 */
    private static final long serialVersionUID = -6315709566933888253L;
    
    /** ID */
    @Id
    @Column(length = 36, nullable = false)
    //@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2") 
    
    @GeneratedValue(generator = "guid")
    @GenericGenerator(name = "guid", strategy = "guid") 
    
    @Length(max = 36)
    protected String id;
    
    /** 创建时间 */
    @Column(name = "creation_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationTime;
    
    /** 修改时间 */
    @Column(name = "modification_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modificationTime;
    
    /** 创建者ID（identifier） */
    @Column(name = "creator_id", length = 255, nullable = true)
    @Length(max = 255)
    protected String creatorId;
    
    /** 所属创建租户的ID（identifier） */
    @Column(name = "creator_tenant_id", length = 255, nullable = true)
    @Length(max = 255)
    protected String creatorTenantId;
    
    /** 更新者ID（identifier） */
    @Column(name = "modifier_id", length = 255, nullable = true)
    @Length(max = 255)
    protected String modifierId;
    
    @Column(name = "modifier_tenant_id", length = 255, nullable = true)
    @Length(max = 255)
    protected String modifierTenantId;
    
    /** 版本号 */
    @Version
    protected Long version;
    
    /**
     * 取创建时间。
     * 
     * @return 创建时间
     */
    public Date getCreationTime() {
    	if(this.creationTime == null){
    		return new Date();
    	}else{
    		return this.creationTime;
    	}
    }
    
    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }
    
    /**
     * 创建者Id
     * 
     * @return 创建者Id
     */
    public String getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(final String creatorId) {
        this.creatorId = creatorId;
    }
    
    public void setCreatorTenantId(final String creatorTenantId) {
        this.creatorTenantId = creatorTenantId;
    }
    
    public void setModifierTenantId(final String modifierTenantId) {
        this.modifierTenantId = modifierTenantId;
    }
    
    /**
     * 更新者Id
     * 
     * @return 更新者Id
     */
    public String getModifierId() {
        return this.modifierId;
    }
    
    public void setModifierId(final String modifierId) {
        this.modifierId = modifierId;
    }
    
    /**
     * 取修改时间。
     * 
     * @return 修改时间
     */
    public Date getModificationTime() {
        return this.modificationTime;
    }
    
    /**
     * 取ID
     * 
     * @return ID
     */
    //@Override  ?? zxl
    public String getId() {
        return this.id;
    }
    
    
    
    public void setId(String id) {
		this.id = id;
	}

	/**
     * 取版本号。
     * 
     * @return 版本号
     */
    //@Override ?? zxl
    public Long getVersion() {
        return this.version;
    }
    
    /**
     * 新建操作前处理。
     */
    @PrePersist
    public void prePersist() {
        if (this.creationTime == null) {
            this.creationTime = new Date();
        }
        if (this.creatorId == null) {
        	if(LoginUserUtils.getSessionLoginUser() != null){
        		this.creatorId = LoginUserUtils.getSessionLoginUser().getId();
        	}
        }
        if (this.creatorTenantId == null) {
        	if(LoginUserUtils.getSessionLoginUser() != null){
        		this.creatorTenantId = LoginUserUtils.getSessionLoginUser().getTenantId();
        	}
        }
    }
    
    /**
     * 更新操作前处理。
     */
    @PreUpdate
    public void preUpdate() {
        this.modificationTime = new Date();
        if (this.modifierId == null) {
        	if(LoginUserUtils.getSessionLoginUser() != null){
        		this.modifierId = LoginUserUtils.getSessionLoginUser().getId();
        	}
        }
        if (this.modifierTenantId == null) {
        	if(LoginUserUtils.getSessionLoginUser() != null){
        		this.modifierTenantId = LoginUserUtils.getSessionLoginUser().getTenantId();
        	}
        }
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof IdVersion)) {
                return false;
        } else {
                return obj.hashCode() == this.hashCode();
        }
    }
    
    /**
     * @return creatorTenantId
     */
    public String getCreatorTenantId() {
        return this.creatorTenantId;
    }
    
    /**
     * @return modifierTenantId
     */
    public String getModifierTenantId() {
        return this.modifierTenantId;
    }
    
    /**
     * @param modificationTime modificationTime
     */
    public void setModificationTime(final Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    
    
}
