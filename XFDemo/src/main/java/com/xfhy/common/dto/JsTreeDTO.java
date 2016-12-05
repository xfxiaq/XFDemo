package com.xfhy.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 用与jsTree 共通DTO
 * @author sqg
 *
 */
public class JsTreeDTO {

	String id;
	String text;
	
	//String state = "{ 'selected' : false,'opened':false }";
//	boolean selected;
//	
//	boolean opened;
	
	//String icon;
	
	Map<String,Boolean> state ;
	
	// 当值为NULL 的时候忽略
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	List<JsTreeDTO> children ;

	public String getText() {
		return text;
	}

//	public boolean isSelected() {
//		return selected;
//	}
//
//	public boolean isOpened() {
//		return opened;
//	}

//	public String getIcon() {
//		return icon;
//	}

	public List<JsTreeDTO> getChildren() {
		return children;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public void setSelected(boolean selected) {
//		this.selected = selected;
//	}
//
//	public void setOpened(boolean opened) {
//		this.opened = opened;
//	}

//	public void setIcon(String icon) {
//		this.icon = icon;
//	}

	public void setChildren(List<JsTreeDTO> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Boolean> getState() {
		return state;
	}

	public void setSelected(Boolean selected) {
		if(state  == null){
			state = new HashMap<String, Boolean>();
			state.put("selected", selected);
		}

	}
	
	public void setOpened( Boolean opened ) {
		if(state  == null){
			state = new HashMap<String, Boolean>();
			state.put("opened", opened);
		}
	}

	
}
