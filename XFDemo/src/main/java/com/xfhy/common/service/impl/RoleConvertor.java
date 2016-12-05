package com.xfhy.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xfhy.common.dto.PageDTO;
import com.xfhy.common.dto.RoleDTO;
import com.xfhy.common.model.Role;



public class RoleConvertor {
	public static Page<RoleDTO> convertToDTOPage(final Pageable pageable,
            final Page<Role> modelPage){
		
		final List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		
		for(Role role  : modelPage.getContent()){
			roleDTOs.add(converToRoleDTO(role));
		}
		
		final Page<RoleDTO> pageDTOs =
                new PageDTO<RoleDTO>(pageable, roleDTOs, modelPage.getTotalElements());
		return pageDTOs;
	}
	
	public static RoleDTO converToRoleDTO(Role role){
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setBaseModel(role);
		roleDTO.setRoleCode(role.getRoleCode());
		roleDTO.setRoleName(role.getRoleName());
		//roleDTO.setFunctions(role.getFunctions());
		return roleDTO;
	}
	
	public static Role converToModel(RoleDTO roleDTO){

		Role role = new Role();
		role.setRoleCode(roleDTO.getRoleCode());
		role.setRoleName(roleDTO.getRoleName());
		//role.setFunctions(roleDTO.getFunctions());
		return role;
	}
}
