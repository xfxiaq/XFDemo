package com.xfhy.common.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.xfhy.common.model.Role;

@Entity
public class User  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907243416904978305L;

	String userId;
	
	String userName;
	
	String phoneNum;
	
	String password;
	
	String tenantId;
	
	/**
	 * 头像ID
	 */
	String avatarId;
	
	boolean enable;
	
	
	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="user_role",
    inverseJoinColumns=@JoinColumn(name="role_id"),
    joinColumns=@JoinColumn(name="user_id"))
	List<Role> roles;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
