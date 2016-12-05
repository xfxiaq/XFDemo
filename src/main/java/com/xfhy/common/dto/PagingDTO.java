package com.xfhy.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;



/**
 * 分页查询DTO。
 * 
 * @author liuyg
 */
public class PagingDTO implements Pageable {
    /** ID */
    protected String id;
    /** 要检索的页 */
    @JsonProperty
    protected Integer page;
    /** 检索要跳过的行数 */
    @JsonProperty
    protected Integer skip;
    /** 每页最大行数 */
    @JsonProperty
    protected Integer pageSize;
    /** XX */
    @JsonProperty
    protected Integer take;
    /** 排序条件 */
    @JsonProperty
    protected SortUnit[] sort;
    /** 分组条件 */
    @JsonProperty
    protected GroupUnit[] group;

    /** 排序过滤器(用于过滤非法字段和前后台字段无法匹配的问题) */
    @JsonIgnore
    protected SortFilter sortFilter;
    
    /**
     * 空构造函数。
     */
    public PagingDTO() {
    }
    
    /**
     * 构造函数。
     * 
     * @param page 要检索的页
     * @param skip 跳过的行数
     * @param pageSize 每页最大行数
     * @param sorts 排序条件
     * @param group 分组条件
     */
    public PagingDTO(final Integer page, final Integer skip, final Integer pageSize, final SortUnit[] sorts,
            final GroupUnit[] group) {
        this.page = page;
        this.skip = skip;
        this.pageSize = pageSize;
        this.sort = sorts;
        this.setGroup(group);
    }
    
    /**
     * 取ID。
     * 
     * @return ID
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * 设置ID
     * 
     * @param id ID
     */
    public void setId(final String id) {
        this.id = id;
    }
    
    /**
     * 设置检索页。
     * 
     * @param page 要检索的页
     */
    @JsonIgnore
    public void setPage(final Integer page) {
        this.page = page;
    }
    
    /**
     * 取检索页（索引从零开始）。
     * 
     * @return 检索页
     */
    @Override
    @JsonIgnore
    public int getPageNumber() {
        return this.page != null ? this.page - 1 : 0;
    }
    
    /**
     * 设置每页最大行数。
     * 
     * @param pageSize 一页最大行数
     */
    @JsonIgnore
    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getPageSize()
     */
    @Override
    @JsonIgnore
    public int getPageSize() {
        return this.pageSize != null ? this.pageSize : 0;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getOffset()
     */
    @Override
    @JsonIgnore
    public int getOffset() {
        return this.getPageNumber() * this.getPageSize();
    }
    
    /**
     * 设置排序条件
     * 
     * @param sort 排序条件
     */
    @JsonIgnore
    public void setSort(final SortUnit[] sort) {
        this.sort = sort;
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getSort()
     */
    @Override
    @JsonIgnore
    public Sort getSort() {
        if ((this.sort == null) || (this.sort.length == 0)) {
            return null;
        }
        SortUnit[] sortArray = this.sort;
        if (this.sortFilter != null) {
            sortArray = this.sortFilter.getFiltedSorts(sortArray);
        }
        final List<Order> orders = new ArrayList<Order>();
        for (final SortUnit order : sortArray) {
            final Sort.Direction dir = Sort.Direction.fromString(order.dir);
            orders.add(new Order(dir, order.field));
        }
        return new Sort(orders);
    }
    
    /**
     * 取查询要跳过的行数。
     * 
     * @return 跳过的行数
     */
    @JsonIgnore
    public Integer getSkip() {
        return this.skip;
    }
    
    /**
     * 设置查询要跳过的行数。
     * 
     * @param skip 跳过的行数
     */
    @JsonIgnore
    public void setSkip(final Integer skip) {
        this.skip = skip;
    }
    
    /**
     * 未知
     * 
     * @return XX
     */
    @JsonIgnore
    public Integer getTake() {
        return this.take;
    }
    
    /**
     * 未知
     * 
     * @param take XX
     */
    @JsonIgnore
    public void setTake(final Integer take) {
        this.take = take;
    }
    
    /**
     * 取分组查询条件。
     * 
     * @return 分组查询条件
     */
    @JsonIgnore
    public GroupUnit[] getGroup() {
        return this.group;
    }
    
    /**
     * 设置分组查询条件。
     * 
     * @param group 分组查询条件
     */
    @JsonIgnore
    public void setGroup(final GroupUnit[] group) {
        this.group = group;
    }
    

    /**
     * @param sortFilter sortFilter
     */
    @JsonIgnore
    public void setSortFilter(final SortFilter sortFilter) {
        this.sortFilter = sortFilter;
    }
    
    /**
     * 排序过滤器。
     * 
     * @author liuyg
     */
    public static class SortFilter {
        /** 允许的排序字段 */
        private final Set<String> sortableFields;
        /** 客户端->数据库 字段字典，用于匹配前后台过滤字段不一致 */
        private final Map<String, String> fieldDict;
        
        /**
         * 构造函数。
         * 
         * @param sortableFields 允许排序字段
         * @param fieldDict 客户端->数据库字段转换字典
         */
        public SortFilter(final Set<String> sortableFields, final Map<String, String> fieldDict) {
            this.sortableFields = sortableFields;
            this.fieldDict = fieldDict;
        }
        
        /**
         * 取过滤后的排序信息。
         * 
         * @param sorts 原始排序信息
         * @return 过滤后的排序信息
         */
        public SortUnit[] getFiltedSorts(final SortUnit[] sorts) {
            if (sorts == null) {
                return null;
            }
            final List<SortUnit> filtedSorts = new ArrayList<SortUnit>();
            for (final SortUnit sortUnit : sorts) {
                String field = sortUnit.field;
                if ((this.sortableFields == null) || this.sortableFields.contains(field)) {
                    if (this.fieldDict.containsKey(field)) {
                        field = this.fieldDict.get(field);
                    }
                    filtedSorts.add(new SortUnit(field, sortUnit.dir));
                }
            }
            return filtedSorts.toArray(new SortUnit[0]);
        }
    }
}
