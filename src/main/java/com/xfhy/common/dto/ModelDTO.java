package com.xfhy.common.dto;



import org.codehaus.jackson.annotate.JsonIgnore;

import com.xfhy.common.model.IdVersion;


/**
 * 
 * @author zxl
 * @version 0.1
 */
public abstract class ModelDTO implements IdVersion {
    /** ID */
    protected String id;
    /** */
    protected Long version;
    /**  */
    protected String _dirty_;
    /**  */
    @JsonIgnore
    protected IdVersion baseModel;
    /**  */

    //@NotNull
    protected String _backcode;
    
    protected String _backmes;
    /**
     * 
     * 
     * @return 
     */
    //@Override  // zxl
    public Long getVersion() {
        return this.baseModel != null ? this.baseModel.getVersion() : this.version;
    }
    
    /**
     * 
     * 
     * @param version
     */
    public void setVersion(final Long version) {
        this.version = version;
    }
    
    /**
     * 
     * 
     * @return ID
     */
    //@Override // zxl
    public String getId() {
        return this.baseModel != null ? this.baseModel.getId() : this.id;
    }
    
    /**
     * 
     * 
     * @param id ID
     */
    public void setId(final String id) {
        this.id = id;
    }
    
    /**
     * 
     * 
     * @param baseModel
     */
    @JsonIgnore
    public void setBaseModel(final IdVersion baseModel) {
        this.id = baseModel.getId();
        this.baseModel = baseModel;
    }
    

    /**
     * @param _dirty_ _dirty_
     */
    public void set_dirty_(final String _dirty_) {
        this._dirty_ = _dirty_;
    }

	public String get_backcode() {
		return _backcode;
	}

	public void set_backcode(String _backcode) {
		this._backcode = _backcode;
	}

	public String get_backmes() {
		return _backmes;
	}

	public void set_backmes(String _backmes) {
		this._backmes = _backmes;
	}
}
