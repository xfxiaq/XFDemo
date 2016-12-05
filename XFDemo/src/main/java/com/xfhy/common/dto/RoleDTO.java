package com.xfhy.common.dto;


public class RoleDTO  extends ModelDTO{
	//List<User> users;
	
	String roleCode;
	
	String roleName;
	
	//List<Function> functions;

//	public List<User> getUsers() {
//		return users;
//	}

	public String getRoleCode() {
		return roleCode;
	}


//	public List<Function> getFunctions() {
//		return functions;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

//	public void setFunctions(List<Function> functions) {
//		this.functions = functions;
//	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
