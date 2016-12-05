package com.xfhy.common.dto;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.domain.Page;

public class JqgridPageDTO <T>{
    //需要显示的数据集  
    private List<T> rows;  
  
    //每页显示数量  
    private int page;  
      
    //数据总数  
    private long records;  
      
    //可显示的页数  
    private int total;  
      
    //自定义数据  
    private Map<String, Object> userdata; 

	public JqgridPageDTO() {
	}
	public JqgridPageDTO(Page<T> dbPage) {
		this.rows = dbPage.getContent();
		this.page = dbPage.getNumber() + 1;
		this.total = dbPage.getSize();
		this.records = dbPage.getTotalElements();
	}
	public List<T> getRows() {
		return rows;
	}
	public int getPage() {
		return page;
	}
	public long getRecords() {
		return records;
	}
	public int getTotal() {
		return total;
	}
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}

	
	
}
