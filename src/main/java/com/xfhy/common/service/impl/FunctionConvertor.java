package com.xfhy.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xfhy.common.dto.FunctionDTO;
import com.xfhy.common.dto.PageDTO;
import com.xfhy.common.model.Function;



public class FunctionConvertor {
	public static Page<FunctionDTO> convertToDTOPage(final Pageable pageable,
            final Page<Function> modelPage){
		
		final List<FunctionDTO> functionDTOs = new ArrayList<FunctionDTO>();
		
		for(Function function  : modelPage.getContent()){
			functionDTOs.add(converToFunctionDTO(function));
		}
		
		final Page<FunctionDTO> pageDTOs =
                new PageDTO<FunctionDTO>(pageable, functionDTOs, modelPage.getTotalElements());
		return pageDTOs;
	}
	
	public static FunctionDTO converToFunctionDTO(Function function){
		FunctionDTO functionDTO = new FunctionDTO();
		functionDTO.setId(function.getId());
		functionDTO.setParentId(function.getParentId());
		functionDTO.setUrlRouter(function.getUrlRouter());
		functionDTO.setFunctionName(function.getFunctionName());
		functionDTO.setFunctionUrl(function.getFunctionUrl());
		functionDTO.setSort(function.getSort());
		functionDTO.setIco(function.getIco());
		return functionDTO;
	}
	
	public static Function converToModel(FunctionDTO functionDTO){

		Function function = new Function();
		function.setId(functionDTO.getId());
		function.setParentId(functionDTO.getParentId());
		function.setUrlRouter(functionDTO.getUrlRouter());
		functionDTO.setFunctionName(function.getFunctionName());
		function.setFunctionUrl(functionDTO.getFunctionUrl());
		function.setSort(functionDTO.getSort());
		function.setIco(functionDTO.getIco());
		return function;
	}
}
