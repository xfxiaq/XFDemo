package com.xfhy.common.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.JsTreeDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.RoleDTO;
import com.xfhy.common.model.Role;



public interface RoleService {
	
	public RoleDTO create(RoleDTO roleDTO);
	
	public RoleDTO update(RoleDTO roleDTO);
	
	public RoleDTO show(String id);
	
	public void delRole(String id);
	
	public Page<FunctionDTO> getFunctionListByRoleId(PagingFilterDTO pagingFilterDTO);
	
	public List<JsTreeDTO> roleFunctionJsTree( String roleId);

	
	public void updateRoleFunction( String roleId,String functionIds);
	
	public Iterable<Role> getRoleSelectList(String param);
	
	public Page<RoleDTO> list(PagingFilterDTO pagingFilterDTO);
}
