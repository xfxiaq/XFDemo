package com.xfhy.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class UserDTO  extends ModelDTO{
	String userId;
	
	String userName;
	
	String phoneNum;
	
	String password;
	
	String roles;
	
	String new_password;
	String repeat_password ;
	
	/**
	 * 头像ID
	 */
	String avatarId;
	
	/**
	 * 用于保存到session中,格式为  xxx,xxxx,xxxx,xxxx
	 */
	String functionUrls;
	/**
	 * 用于用户编辑页面返回的设置好的角色
	 */
	List<Map<String,String>> defaultRoles = new ArrayList<Map<String,String>>();

	boolean enable;
	
	String tenantId;
	
	String tokenId;
	List<Integer> tenantIds;
	
	
	
	

	
   
	
	

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<Map<String, String>> getDefaultRoles() {
		return defaultRoles;
	}

	public void setDefaultRoles(List<Map<String, String>> defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	public String getFunctionUrls() {
		return functionUrls;
	}

	public void setFunctionUrls(String functionUrls) {
		this.functionUrls = functionUrls;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getNew_password() {
		return new_password;
	}

	public String getRepeat_password() {
		return repeat_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public void setRepeat_password(String repeat_password) {
		this.repeat_password = repeat_password;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	public List<Integer> getTenantIds() {
		return tenantIds;
	}

	public void setTenantIds(List<Integer> tenantIds) {
		this.tenantIds = tenantIds;
	}
	
	
}
