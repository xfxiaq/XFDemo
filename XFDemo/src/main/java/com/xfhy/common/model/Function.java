package com.xfhy.common.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;


/**
 * 角色功能。只支持二级
 * @author sqg
 *
 */
@Entity 
public class Function  extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7119993418228033984L;
	
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="functions")
	List<Role> roles;
	
	String parentId;
	
	String  functionName;
	
	String  functionUrl;
	
	String urlRouter;  
	
	/**
	 * 是否启用
	 */
	boolean enable;
	
	/**
	 * 对应权限的URL, 用于权限控制,设置的值是以及菜单访问的列表页面的URL, 比如  xxxxList.json
	 */
	String authUrl;
	
	/**
	 * 是否预置， 预置的不可删除。预置的都作为顶级菜单。
	 */
	boolean isPreset = false;
	
	/**
	 * 排序
	 */
	int sort;
	
	/**
	 * 菜单上的图标
	 */
	String ico;

	public String getUrlRouter() {
		return urlRouter;
	}

	public void setUrlRouter(String urlRouter) {
		this.urlRouter = urlRouter;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isPreset() {
		return isPreset;
	}

	public void setPreset(boolean isPreset) {
		this.isPreset = isPreset;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

}
