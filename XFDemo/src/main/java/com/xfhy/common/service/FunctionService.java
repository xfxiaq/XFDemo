package com.xfhy.common.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.LeftMenuDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.model.Function;



public interface FunctionService {
	public FunctionDTO create(FunctionDTO functionDTO);
	
	public FunctionDTO getFunction(String id);
	
	public FunctionDTO update(FunctionDTO functionDTO);
	
	public void delFunction(String id);
	
	public Page<FunctionDTO> list(PagingFilterDTO pagingFilterDTO ) ;
	
	/**
	 * 根据角色获取功能列表
	 * @param ownRoles
	 * @return
	 */
	List<LeftMenuDTO> getPersionalFunctionList(String ownRoles );
	
	Iterable<Function> getParentFunctionList(String param,String selfId);
	
	public String getPersionalFunctionUrls(String ownRoles );
}
