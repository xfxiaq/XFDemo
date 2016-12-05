package com.xfhy.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.JsTreeDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.RoleDTO;
import com.xfhy.common.dto.TextValueDTO;
import com.xfhy.common.model.Role;
import com.xfhy.common.service.RoleService;



@Controller
@RequestMapping(value = "/manage/systemSetting/role/")
public class RoleController {
	
	@Resource
	RoleService roleService;

    @RequestMapping(value = "create.json",method = RequestMethod.POST)
    @ResponseBody
	public RoleDTO create(@RequestBody RoleDTO roleDTO){
		
    	RoleDTO backRoleDTO = roleService.create(roleDTO);
    	
    	return backRoleDTO;
	}
    
    @RequestMapping(value = "update.json",method = RequestMethod.POST)
    @ResponseBody
	public RoleDTO update(@RequestBody RoleDTO roleDTO){
		
    	RoleDTO backRoleDTO = roleService.update(roleDTO);
    	
    	return backRoleDTO;
	}
    
    @RequestMapping(value = "show.json",method = RequestMethod.POST)
    @ResponseBody
	public RoleDTO show(@Valid @RequestBody final RoleDTO roleDTO){
    	return roleService.show(roleDTO.getId());
	}
    
    @RequestMapping(value = "delRole.json/{id}",method = RequestMethod.POST)
    @ResponseBody
	public RoleDTO delRole(@PathVariable String id){
		
    	roleService.delRole(id);
    	
    	return new RoleDTO();
	}
    
    @RequestMapping(value = "auth_menu_list.json",method = RequestMethod.POST)
    @ResponseBody
	public Object list(@RequestBody PagingFilterDTO pagingFilterDTO){    	
//    	PagingFilterDTO pagingFilterDTO = new PagingFilterDTO();
//    	
//    	pagingFilterDTO.setPage(Integer.valueOf( page));
//    	pagingFilterDTO.setPageSize(Integer.valueOf( pageSize));
//    	pagingFilterDTO.setSort(new SortUnit[]{new SortUnit(sidx,sord)});
//    	
    	//return  roleService.list(pagingFilterDTO);
    	
    	Page<RoleDTO> rs =  roleService.list(pagingFilterDTO);
    	/*
    	JqgridDataResponse<RoleDTO> response = new JqgridDataResponse<RoleDTO>();  
    	
    	List<RoleDTO> jqgridDatas = new ArrayList<RoleDTO>();
    	System.out.println(rs.getContent().getClass());
    	for(RoleDTO temp : rs.getContent()){
        	RoleDTO a1 = new RoleDTO();
        	a1.setId(temp.getId());
        	a1.setRoleCode(temp.getRoleCode());
        	a1.setRoleName(temp.getRoleName());
        	jqgridDatas.add(a1);
    	}
    	response.setRows ( rs.getContent());  
        response.setTotal(rs.getTotalPages());  
        response.setPage(rs.getNumber());  
        response.setRecords(rs.getTotalElements()); 
        */
    	return rs;
	}
  
    /**
     * 获取角色对应的功能权限.
     * @param id
     * @return
     */
    @RequestMapping(value = "roleFunctions.json",method = RequestMethod.POST)
    @ResponseBody
	public Page<FunctionDTO> roleFunctions(@RequestBody PagingFilterDTO pagingFilterDTO){    	
    	
    	//Page<RoleDTO> rs =  roleService.list(pagingFilterDTO);
    	return this.roleService.getFunctionListByRoleId(pagingFilterDTO);
	}
    
    /**
     * 获取角色对应的功能权限. 树形结构
     * @param id
     * @return
     */
    @RequestMapping(value = "roleFunctionJsTree.json/{id}",method = RequestMethod.POST)
    @ResponseBody
	public List<JsTreeDTO> roleFunctionJsTree(@PathVariable String id){    	
    	
    	//Page<RoleDTO> rs =  roleService.list(pagingFilterDTO);
    	return this.roleService.roleFunctionJsTree(id);
	}

    /**
     * 保存角色对应的权限列表
     * @param id
     * @return
     */
    @RequestMapping(value = "updateRoleFunction.json",method = RequestMethod.POST)
    @ResponseBody
	public Boolean updateRoleFunction(@RequestBody Map<String,String> param){    	
    	
    	String roleId = param.get("roleId");
    	String functionIds = param.get("ids");
    	this.roleService.updateRoleFunction(roleId, functionIds);
    	return true;
	}


    /**
     * 获取角色下拉列表
     * @param map
     * @return
     */
    @RequestMapping(value = "getRoleSelectList.json",method = RequestMethod.POST)
    @ResponseBody
	public List<TextValueDTO> getRoleSelectList(@RequestBody Map<String,String> map){
    	Iterable<Role> lists = roleService.getRoleSelectList(map.get("param").toString());
    	List<TextValueDTO> result = new ArrayList<TextValueDTO>();
    	for( Role s : lists){
    		TextValueDTO t = new TextValueDTO();
    		t.setText( s.getRoleName());
    		t.setId(s.getId());
    		result.add(t);
    	}
    	
    	return result;
	}
    
}
