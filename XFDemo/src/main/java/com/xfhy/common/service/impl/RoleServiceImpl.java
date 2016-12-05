package com.xfhy.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.xfhy.common.dto.Filter;
import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.JsTreeDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.RoleDTO;
import com.xfhy.common.model.Function;
import com.xfhy.common.model.QFunction;
import com.xfhy.common.model.QRole;
import com.xfhy.common.model.Role;
import com.xfhy.common.repository.FunctionRepository;
import com.xfhy.common.repository.RoleRepository;
import com.xfhy.common.repository.UserRepository;
import com.xfhy.common.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService{
	
	@Resource
	RoleRepository roleRepository;
	@Resource
	FunctionRepository functionRepository;
	@Resource
	UserRepository userRepository;

	
	public RoleDTO create(RoleDTO roleDTO){
		Role role = RoleConvertor.converToModel(roleDTO);		
		role = this.roleRepository.save(role);
		roleDTO.setId(role.getId());
		return roleDTO;
	}
	
	public RoleDTO update(RoleDTO roleDTO){
		Role role = roleRepository.findOne(roleDTO.getId());
		role.setRoleCode(roleDTO.getRoleCode());
		role.setRoleName(roleDTO.getRoleName());
		role = this.roleRepository.save(role);
		return RoleConvertor.converToRoleDTO(role);
	}
	
	public RoleDTO show(String id){
		Role role = roleRepository.findOne(id);
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setRoleCode(role.getRoleCode());
		roleDTO.setRoleName(role.getRoleName());
		return roleDTO;
	}
	
	/**
	 * 
	 */
	@Transactional
	public void delRole(String id){
		// 删除关联表
		Role role = this.roleRepository.findOne(id);
		this.functionRepository.delete(role.getFunctions());
		
		// 删除主表
		this.roleRepository.delete(id);		
	}
	
	@Override
	@Transactional
	public Page<RoleDTO> list(PagingFilterDTO pagingFilterDTO){
		final Filter filter = pagingFilterDTO.getFilter();
		
		final BooleanBuilder where = new BooleanBuilder();
		
		if(filter != null){
			if(filter.containsField("roleCode")){
				where.and(QRole.role.roleCode.contains(filter.getValue("roleCode")));
			}
		} 		
		
		Page<Role> roles = this.roleRepository.findAll(where.getValue(),pagingFilterDTO);

		return RoleConvertor.convertToDTOPage(pagingFilterDTO, roles);
	}
	
	/**
	 * 根据角色获取功能列表
	 */
	@Transactional
	public Page<FunctionDTO> getFunctionListByRoleId(PagingFilterDTO pagingFilterDTO){
		final Filter filter = pagingFilterDTO.getFilter();
		final BooleanBuilder where = new BooleanBuilder();
		if(filter != null){
			if(filter.containsField("id")){
				where.and(QFunction.function.roles.contains( this.roleRepository.findOne(filter.getValue("id"))));
			}
		} 	
		
		Page<Function> functions =  this.functionRepository.findAll(where.getValue(),pagingFilterDTO);
		
		return FunctionConvertor.convertToDTOPage(pagingFilterDTO, functions);
	}
	
	/**
	 * 获取角色对应的功能权限. 树形结构.
	 * 要从session里面获取用户ID 
	 */
	@Transactional(readOnly=true)
	public List<JsTreeDTO> roleFunctionJsTree( String roleId){
		
		// 获取顶级菜单功能权限
		List<Function> allParentFunctions = (List<Function>)  this.functionRepository.findAll
				(QFunction.function.parentId.isNull().or(QFunction.function.parentId.isEmpty()),QFunction.function.sort.asc());


		// 获取用户角色,
		Role  role = this.roleRepository.findOne(roleId);
		
		// 获取该角色已经设置了的功能权限
		List<Function> existFunction = role.getFunctions();
		
		List<JsTreeDTO> returnList = new ArrayList<JsTreeDTO>();
		for(Function parentFunction : allParentFunctions){
			JsTreeDTO parentJtd = new JsTreeDTO();
			parentJtd.setId(parentFunction.getId());
			parentJtd.setText(parentFunction.getFunctionName());
			if(existFunction.contains(parentFunction)){
				parentJtd.setSelected(true);
			}
			// 获取该菜单的子菜单
			List<Function> childFunctions =  (List<Function>) this.functionRepository.findAll(QFunction.function.parentId.eq(parentFunction.getId()),QFunction.function.sort.asc());
			
			if(childFunctions != null && childFunctions.size() > 0){
				List<JsTreeDTO> childList = new ArrayList<JsTreeDTO>();
				for(Function childFunction : childFunctions){
					JsTreeDTO childJtd = new JsTreeDTO();
					childJtd.setId(childFunction.getId());
					childJtd.setText(childFunction.getFunctionName());
					if(existFunction.contains(childFunction)){
						childJtd.setSelected(true);
					}
					childList.add(childJtd);
				}
				if(childList != null && childList.size() > 0){
					parentJtd.setChildren(childList);
				}
			}
			returnList.add(parentJtd);
		}
		return returnList;
	}
	public List<FunctionDTO> convertToListDTO(List<Function> functions){
		List<FunctionDTO>  functionDTOs = new ArrayList<FunctionDTO>();
		
		for(Function f : functions){
			FunctionDTO fd = new FunctionDTO();
			fd.setParentId(f.getParentId());
			if(f.getParentId() != null && !"".equals(f.getParentId())){
				fd.setParentName(this.functionRepository.findOne(f.getParentId()).getFunctionName());
			}else{
				fd.setParentName("");
			}
			fd.setFunctionName(f.getFunctionName());
			fd.setFunctionUrl(f.getFunctionUrl());
			functionDTOs.add(fd);
		}
		return functionDTOs;
	}
	
	/**
	 * 更新角色下的权限列表
	 */
	@Transactional
	public void updateRoleFunction( String roleId,String functionIds){
		
		// 先删除
		Role role = this.roleRepository.findOne(roleId);			
		role.setFunctions(null);
		role = this.roleRepository.save(role);
		
		List<Function> functions = new ArrayList<Function>();
		// 再添加
		if(!"".equals(functionIds)){
				String [] functionArray = functionIds.split(",");
				for(String functionId : functionArray){
					if("".equals(functionId)){
						continue;
					}
					Function fuction = this.functionRepository.findOne(functionId);
					functions.add(fuction);
				}
				role.setFunctions(functions);
		}
		this.roleRepository.save(role);
	}
	
	/**
	 * 获取角色下拉列表
	 */
	public Iterable<Role> getRoleSelectList(String param){		
		return this.roleRepository.findAll(QRole.role.roleName.contains(param).or(QRole.role.roleCode.contains(param)));
	}



}
