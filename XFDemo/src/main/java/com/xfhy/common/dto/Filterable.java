package com.xfhy.common.dto;

import org.springframework.data.domain.Sort;




/**
 * 查询过滤条件接口。
 * 
 * @author liuyg
 * @version 1.0
 */
public interface Filterable  {
    
    /**
     * 取得查询过滤条件。
     * 
     * @return 查询过滤条件
     */
    public Filter getFilter();
    
    /**
     * 取得排序条件。
     * 
     * @return 排序条件
     */
    public Sort getSort();
    
    /**
     * 取得分组条件。
     * 
     * @return 分组条件
     */
    public GroupUnit[] getGroup();
}
