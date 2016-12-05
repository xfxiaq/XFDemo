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
import com.xfhy.common.dto.LeftMenuDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.TextValueDTO;
import com.xfhy.common.dto.UserDTO;
import com.xfhy.common.model.Function;
import com.xfhy.common.service.FunctionService;
import com.xfhy.common.utils.LoginUserUtils;



@Controller
@RequestMapping(value = "/manage/systemSetting/function/")
public class FunctionController {

	@Resource
	FunctionService functionService;
	
    @RequestMapping(value = "auth_menu_list.json",method = RequestMethod.POST)
    @ResponseBody
	public Page<FunctionDTO> list(@Valid @RequestBody final PagingFilterDTO pagingFilterDTO){
    	final Page<FunctionDTO> productList = functionService.list(pagingFilterDTO);
    	return productList;
    }
    
    @RequestMapping(value = "create.json",method = RequestMethod.POST)
    @ResponseBody
	public FunctionDTO create(@Valid @RequestBody final FunctionDTO functionDTO){
    	return functionService.create(functionDTO);
	}
    
    @RequestMapping(value = "show.json",method = RequestMethod.POST)
    @ResponseBody
	public FunctionDTO show(@Valid @RequestBody final FunctionDTO functionDTO){
    	return functionService.getFunction(functionDTO.getId());
	}
    
    
    @RequestMapping(value = "update.json",method = RequestMethod.POST)
    @ResponseBody
	public FunctionDTO update(@Valid @RequestBody final FunctionDTO functionDTO){
    	return functionService.update(functionDTO);
	}
    
    @RequestMapping(value = "delFunction.json/{id}",method = RequestMethod.POST)
    @ResponseBody
	public FunctionDTO delFunction(@PathVariable String id){    	
    	this.functionService.delFunction(id);
    	return new FunctionDTO();
	}
    
    /**
     * 获取父菜单数据，用于展现下拉列表
     * @param param
     * @return
     */
    @RequestMapping(value = "getParentFunctionList.json",method = RequestMethod.POST)
    @ResponseBody
	public List<TextValueDTO> getParentFunctionList(@RequestBody Map<String,String> map){
    	
    	Iterable<Function> lists = functionService.getParentFunctionList(map.get("param"),map.get("selfId"));
    	List<TextValueDTO> result = new ArrayList<TextValueDTO>();
    	for( Function s : lists){
    		TextValueDTO t = new TextValueDTO();
    		t.setText(s.getFunctionName());
    		t.setId(s.getId());
    		result.add(t);
    	}
    	return result;
	}
    
    /**
     * 获取用户拥有的权限菜单列表
     * @param param
     * @return
     */
    @RequestMapping(value = "getPersionalFunctionList.json",method = RequestMethod.POST)
    @ResponseBody
	public List<LeftMenuDTO> getPersionalFunctionList(){
		UserDTO userDTO = LoginUserUtils.getSessionLoginUser();
		// 该用户拥有的角色
		String ownRoles = userDTO.getRoles();//ownRoles是ID的String形式
    	return this.functionService.getPersionalFunctionList(ownRoles);
	}
    
    
}
