package com.xfhy.common.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


/**
 * 用户 role
 * @author sqg
 *
 */
@Entity 
public class Role extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7837804356266599097L;

	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="roles")
	List<User> users;
	
	String roleCode;
	
	String roleName;
	
	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="role_function",
    inverseJoinColumns=@JoinColumn(name="function_id"),
    joinColumns=@JoinColumn(name="role_id"))
	List<Function> functions;
	

	public List<User> getUsers() {
		return users;
	}

	public String getRoleCode() {
		return roleCode;
	}



	public List<Function> getFunctions() {
		return functions;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
