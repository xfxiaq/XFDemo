package com.xfhy.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.xfhy.common.dto.Filter;
import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.LeftMenuDTO;
import com.xfhy.common.dto.PageDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.model.Function;
import com.xfhy.common.model.QFunction;
import com.xfhy.common.repository.FunctionRepository;
import com.xfhy.common.service.FunctionService;


@Service
public class FunctionServiceImpl implements FunctionService	{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Resource
	FunctionRepository functionRepository;

	public Page<FunctionDTO> list(PagingFilterDTO pagingFilterDTO ) {
		final Filter filter = pagingFilterDTO.getFilter();
		
		final BooleanBuilder where = new BooleanBuilder();
		
		if(filter != null){
			if(filter.containsField("functionName")){
				where.and(QFunction.function.functionName.contains(filter.getValue("functionName")));
			}
			if(filter.containsField("functionUrl")){
				where.and(QFunction.function.functionUrl.contains(filter.getValue("functionUrl")));
			}
			if(filter.containsField("parentId")){
				where.and(QFunction.function.parentId.eq(filter.getValue("parentId")));
			}
		} 		
		
		Page<Function> functions = this.functionRepository.findAll(where.getValue(),pagingFilterDTO);

		final List<FunctionDTO> functionDTOs = new ArrayList<FunctionDTO>();
		
		for(Function function  : functions.getContent()){
			FunctionDTO functionDTO = new FunctionDTO();
			functionDTO.setId(function.getId());
			functionDTO.setParentId(function.getParentId());
			functionDTO.setUrlRouter(function.getUrlRouter());
			functionDTO.setAuthUrl(function.getAuthUrl());
			if(function.getParentId() != null && !"".equals(function.getParentId())){
				functionDTO.setParentName(this.functionRepository.findOne(function.getParentId()).getFunctionName());
			}else{
				functionDTO.setParentName("");
			}
			functionDTO.setFunctionName(function.getFunctionName());
			functionDTO.setFunctionUrl(function.getFunctionUrl());
			functionDTO.setSort(function.getSort());
			functionDTO.setIco(function.getIco());
			functionDTOs.add(functionDTO);
		}
		
		return new PageDTO<FunctionDTO>(pagingFilterDTO, functionDTOs, functions.getTotalElements());
	}

	public FunctionDTO create(FunctionDTO functionDTO) {
		Function function = new Function();
		function.setParentId(functionDTO.getParentId());
		function.setFunctionName(functionDTO.getFunctionName());
		function.setFunctionUrl(functionDTO.getFunctionUrl());
		function.setUrlRouter(functionDTO.getUrlRouter());
		function.setAuthUrl(functionDTO.getAuthUrl());
		function.setSort(functionDTO.getSort());
		function.setIco(functionDTO.getIco());
		functionRepository.save(function);
		functionDTO.setId(function.getId());
		return functionDTO;
	}
	
	public FunctionDTO getFunction(String id){
		Function function = this.functionRepository.findOne(id);
		FunctionDTO functionDTO = new FunctionDTO();
		functionDTO.setParentId(function.getParentId());
		if(function.getParentId() != null && !"".equals(function.getParentId())){
			functionDTO.setParentName(this.functionRepository.findOne(function.getParentId()).getFunctionName());
		}else{
			functionDTO.setParentName("");
		}
		functionDTO.setFunctionName(function.getFunctionName());
		functionDTO.setFunctionUrl(function.getFunctionUrl());	
		functionDTO.setUrlRouter(function.getUrlRouter());
		functionDTO.setAuthUrl(function.getAuthUrl());
		functionDTO.setSort(function.getSort());
		functionDTO.setId(function.getId());
		functionDTO.setIco(function.getIco());
		 return functionDTO;
	}

	public FunctionDTO update(FunctionDTO functionDTO) {
		Function function = functionRepository.findOne(functionDTO.getId());
		function.setParentId(functionDTO.getParentId());
		function.setFunctionName(functionDTO.getFunctionName());
		function.setFunctionUrl(functionDTO.getFunctionUrl());
		function.setUrlRouter(functionDTO.getUrlRouter());
		function.setAuthUrl(functionDTO.getAuthUrl());
		function.setSort(functionDTO.getSort());
		function.setIco(functionDTO.getIco());
		functionRepository.save(function);
		return functionDTO;
	}
	
	public void delFunction(String id){
		Function function = functionRepository.findOne(id);
		this.functionRepository.delete(function);
	}
	
	/**
	 * 获取父菜单数据，用于展现下拉列表
	 */
	public Iterable<Function> getParentFunctionList(String param,String selfId){
		
		BooleanBuilder where = new BooleanBuilder();
		where.and(QFunction.function.functionName.contains(param));
		where.and(QFunction.function.id.ne(selfId));
		where.and(QFunction.function.parentId.isNull().or(QFunction.function.parentId.isEmpty()));
		//where.and(QFunction.function.isPreset.eq(true));
		return this.functionRepository.findAll(where);
	}
	
	/**
	 * 根据角色获取功能列表, 这个功能为了提升效率,使用原生sql实现
	 * @param ownRoles
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<LeftMenuDTO> getPersionalFunctionList(String ownRoles ){

		List<LeftMenuDTO> returnMenus = new ArrayList<LeftMenuDTO>();
		
		String roles = "";
		if(!"".equals(ownRoles)){
			String [] arrayRoles = ownRoles.split(",");
			roles += "(" ;
			for(String roleId : arrayRoles){
				roles  += "'" + roleId + "',";			
			}
			
			if(roles.endsWith(",")){
				roles = roles.substring(0,roles.length() -1) + ")";
			}
		}
		StringBuilder sqlBul = new StringBuilder();
		
		sqlBul.append("	select distinct allFunction.id, allFunction.function_name, allFunction.function_url,  allFunction.url_router ,allFunction.ico   , allFunction.sort ");
		sqlBul.append("	from  ");
		sqlBul.append("	(  ");
		
		sqlBul.append("	 select distinct fparent.id, fparent.function_name, fparent.function_url, fparent.url_router ,fparent.ico , fparent.sort ");
		sqlBul.append("	  from role_function rf 	 ");
		sqlBul.append("	 inner join function f on rf.function_id = f.id	 ");
		sqlBul.append("	 inner join function fparent on f.parent_id = fparent.id ");
		sqlBul.append("	where rf.role_id in  " + roles + " ");
		
		sqlBul.append("	union  ");
		
		sqlBul.append("	 select distinct fparent.id, fparent.function_name, fparent.function_url, fparent.url_router ,fparent.ico  , fparent.sort ");
		sqlBul.append("	  from role_function rf 	 ");
		sqlBul.append("	 inner join function fparent on rf.function_id = fparent.id ");
		sqlBul.append("	where rf.role_id in  " + roles + " ");
		sqlBul.append("   and  (fparent.parent_id is null  or  fparent.parent_id = '' )");
		//sqlBul.append("	order by  fparent.sort asc	 ");
		
		sqlBul.append(" ) allFunction  ");
		sqlBul.append("	order by  allFunction.sort asc	 ");
		
		System.out.println("---");
		System.out.println(sqlBul.toString());
		System.out.println("---");
		
		// 查询父菜单.
		Query queryParent = this.entityManager.createNativeQuery(sqlBul.toString());
		
		List functionParents = queryParent.getResultList();

		for(int i=0;i<functionParents.size(); i++){
			Object[] p = (Object[])functionParents.get(i);
			LeftMenuDTO leftParentMenuDTO =new LeftMenuDTO();
			leftParentMenuDTO.setIsParent(true);
			leftParentMenuDTO.setFunctionName((String)p[1]);
			leftParentMenuDTO.setFunctionUrl((String)p[2]);
			leftParentMenuDTO.setUrlRouter((String)p[3]);
			leftParentMenuDTO.setIco((String)p[4]);
			
			StringBuilder sqlChild = new StringBuilder();
			String parentId = (String)p[0];
			sqlChild.append("	select distinct fchild.id, fchild.function_name, fchild.function_url, fchild.url_router,fchild.ico 	 ");
			sqlChild.append("	from function fchild	,role_function rf ");
			sqlChild.append(" where fchild.id = rf.function_id ");
			sqlChild.append(" and fchild.parent_id = '" + parentId + "' ");
			sqlChild.append(" and rf.role_id in " + roles + " ");
			sqlChild.append(" order by  fchild.sort asc ");
			//sqlChild.append("	where fchild.parent_id = 	 '" + parentId + "' ");
			//sqlChild.append("	order by  fchild.sort asc	 ");
			
			Query queryChild = this.entityManager.createNativeQuery(sqlChild.toString());
			
			List functionChildrenList = queryChild.getResultList();
			
			List<LeftMenuDTO> functionChildren = new ArrayList<LeftMenuDTO>();
			for(int j = 0; j < functionChildrenList.size() ; j++){
				LeftMenuDTO leftChildMenuDTO =new LeftMenuDTO();
				Object[] childF = (Object[])functionChildrenList.get(j);
				leftChildMenuDTO.setIsParent(false);
				leftChildMenuDTO.setFunctionName((String)childF[1]);
				leftChildMenuDTO.setFunctionUrl((String)childF[2]);
				leftChildMenuDTO.setUrlRouter((String)childF[3]);
				leftChildMenuDTO.setIco((String)childF[4]);
				functionChildren.add(leftChildMenuDTO);
			}
			
			leftParentMenuDTO.setChildren(functionChildren);
			
			returnMenus.add(leftParentMenuDTO);
		}
		
		return returnMenus;
	}
	
	/**
	 * 获取简单的根据角色获取的所有权限URL集合,返回格式为xxx,xxx,xxx,xxx,
	 * @param ownRoles
	 * @return
	 */
	@Transactional(readOnly = true)
	public String getPersionalFunctionUrls(String ownRoles ){
		
		String allFunctions = "";
		
		String roles = "";
		if(!"".equals(ownRoles)){
			String [] arrayRoles = ownRoles.split(",");
			roles += "(" ;
			for(String roleId : arrayRoles){
				roles  += "'" + roleId + "',";			
			}
			
			if(roles.endsWith(",")){
				roles = roles.substring(0,roles.length() -1) + ")";
			}
		}
		StringBuilder sqlBul = new StringBuilder();
		
		sqlBul.append("	 select distinct f.auth_url ");
		sqlBul.append("	  from role_function rf 	 ");
		sqlBul.append("	 inner join function f on rf.function_id = f.id ");
		sqlBul.append("	where rf.role_id in  " + roles + " and f.auth_url is not null and f.auth_url <> ''  ");		
		Query queryAll= this.entityManager.createNativeQuery(sqlBul.toString());
		
		List allFunctionList = queryAll.getResultList();
		
		for(int i=0;i<allFunctionList.size(); i++){
			String p = allFunctionList.get(i).toString();
			
			allFunctions += p + ",";
		}
		
		if(allFunctions.endsWith(",")){
			allFunctions = allFunctions.substring(0,allFunctions.length() - 1) ;
		}
		return allFunctions;
	}


}
