package com.xfhy.common.dto;

import java.util.ArrayList;
import java.util.List;


public class LeftMenuDTO {
	
	String id;
	/**
	 * 是否是父菜单
	 */
	Boolean isParent;
	
	String  functionName;
	
	String  functionUrl;
	
	String urlRouter;  

	// 子节点
	List<LeftMenuDTO> children = new ArrayList<LeftMenuDTO>();

	String ico;
	
	public String getId() {
		return id;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public String getFunctionName() {
		return functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public String getUrlRouter() {
		return urlRouter;
	}

	public List<LeftMenuDTO> getChildren() {
		return children;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public void setUrlRouter(String urlRouter) {
		this.urlRouter = urlRouter;
	}

	public void setChildren(List<LeftMenuDTO> children) {
		this.children = children;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

}
