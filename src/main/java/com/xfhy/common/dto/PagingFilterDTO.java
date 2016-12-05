package com.xfhy.common.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


import com.xfhy.common.dto.Filter;
import com.xfhy.common.dto.Filter.FilterSuit;
import com.xfhy.common.dto.Filter.Logic;
import com.xfhy.common.exception.BaseAppRuntimeException;
import com.xfhy.common.utils.JsonUtils;




/**
 * 分页查询DTO。
 * 
 * @author liuyg
 * @version 1.0
 */
public class PagingFilterDTO extends PagingDTO implements Filterable {
    
    /** 客户端传送的原始过滤条件MAP */
    @JsonProperty
	private Map<String, Object> filter;
    
    /**
     * 设置客户端的原始过滤条件MAP。
     * 
     * @param filter 过滤条件
     */
    @JsonIgnore
    public void setFilter(final Map<String, Object> filter) {
        this.filter = filter;
    }
    
    /**
     * 添加过滤条件（默认与其他过滤条件的逻辑关系为 AND）。
     * 
     * <pre>
     * <li>注意：该函数只能将过滤条件追加到条件树结构的第一层
     * </pre>
     * 
     * @param filters 过滤条件集合
     */
    @JsonIgnore
    public void addFilters(final Filter... filters) {
        this.addFilters(Logic.and, filters);
    }
    
    /**
     * 添加过滤条件（默认与其他过滤条件的逻辑关系为 AND）。
     * 
     * <pre>
     * <li>注意：该函数只能将过滤条件追加到条件树结构的第一层
     * </pre>
     * 
     * @param logic 过滤条件间的逻辑关系
     * @param filters 过滤条件集合
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    public void addFilters(final Logic logic, final Filter... filters) {
        if ((filters == null) || (filters.length == 0)) {
            return;
        }
        if (this.filter == null) {
            this.filter = new HashMap<String, Object>();
        }
        if (this.filter.size() == 0) {
            this.filter = new HashMap<String, Object>();
            this.filter.put(Filter.LOGIC, Logic.and);
            this.filter.put(Filter.FILTERS, new ArrayList<Map>());
        }
        if (this.isFilterLeaf(this.filter)) {
            final Map<String, Object> newFilter = new HashMap<String, Object>();
            newFilter.put(Filter.LOGIC, Logic.and);
            final List<Map> tmpFilters = new ArrayList<Map>();
            tmpFilters.add(this.filter);
            newFilter.put(Filter.FILTERS, tmpFilters);
            this.filter = newFilter;
        }
        // 组合过滤条件
        if (this.isFilterSuit(this.filter)) {
            final Object logicParam = this.filter.get(Filter.LOGIC);
            Filter.Logic originalLogic = Filter.Logic.and;
            if (logicParam != null) {
                if (logicParam instanceof String) {
                    originalLogic = Filter.Logic.valueOf((String) logicParam);
                } else {
                    originalLogic = (Logic) logicParam;
                }
            }
            
            // 逻辑关系相同，加入到原过滤条件集合里
            if (originalLogic == logic) {
                final List originalFilters = (List) this.filter.get(Filter.FILTERS);
                for (final Filter filter : filters) {
                    final Map filterMap = JsonUtils.pojoToMap(filter);
                    if (!filter.isCondition()) {
                        originalFilters.addAll((List) filterMap.get(Filter.FILTERS));
                    } else {
                        originalFilters.add(filterMap);
                    }
                }
            } else { // 逻辑关系不同，构造并列的条件
                final Map<String, Object> oldFilter = this.filter;
                
                final Map<String, Object> addFilter = new HashMap<String, Object>();
                final List<Map> addFilterChildren = new ArrayList<Map>();
                for (final Filter tmpfilter : filters) {
                    final Map filterMap = JsonUtils.pojoToMap(tmpfilter);
                    addFilterChildren.add(filterMap);
                }
                addFilter.put(Filter.LOGIC, logic);
                addFilter.put(Filter.FILTERS, addFilterChildren);
                
                final Map<String, Object> newFilter = new HashMap<String, Object>();
                final List<Map> newFilterChildren = new ArrayList<Map>();
                newFilter.put(Filter.LOGIC, Logic.and);
                newFilter.put(Filter.FILTERS, newFilterChildren);
                newFilterChildren.add(oldFilter);
                newFilterChildren.add(addFilter);
                
                this.filter = newFilter;
            }
        }
    }
    
    /**
     * 取后台查询用的格式化后的查询过滤条件。
     * 
     * @return 格式化后的查询过滤条件
     */
    @Override
    @JsonIgnore
    public Filter getFilter() {
        return this.loadFilter(this.filter);
    }
    
    /**
     * 构造化客户端的过滤条件为更易于使用的Filter对象。
     * 
     * @param filter 客户端过滤条件
     * @return Filter对象
     */
    @JsonIgnore
    private Filter loadFilter(final Map<String, Object> filter) {
        if ((filter == null) || (filter.size() == 0)) {
            final Filter[] filters = null;
            return new FilterSuit(null, filters);
        }
        if (this.isFilterSuit(filter)) {
            return this.loadFilterSuit(filter);
        }
        if (this.isFilterLeaf(filter)) {
            return this.loadLeafFilter(filter);
        }
        throw new BaseAppRuntimeException("json.filter.invalidformat");
    }
    
    /**
     * 构造化基本结构（非嵌套）的客户端过滤条件为更易于使用的Filter对象。
     * 
     * @param filter 客户端过滤条件
     * @return Filter对象
     */
    @JsonIgnore
    private Filter loadLeafFilter(final Map<String, Object> filter) {
        final String field = (String) filter.get(Filter.FIELD);
        final Filter.Operator operator = Filter.Operator.valueOf((String) filter.get(Filter.OPERATOR));
        final Object value = filter.get(Filter.VALUE);
        return new Filter.Condition(field, operator, value);
    }
    
    /**
     * 构造化复杂结构（组合嵌套）的客户端过滤条件为更易于使用的Filter对象。
     * 
     * @param filter 客户端过滤条件
     * @return Filter对象
     */
    @JsonIgnore
    @SuppressWarnings({"rawtypes", "unchecked" })
    private Filter loadFilterSuit(Map<String, Object> filterMap) {
        if (filterMap.containsKey(Filter.FILTER)) {
            filterMap = (Map<String, Object>) filterMap.get(Filter.FILTER);
        }
        final Object logicParam = filterMap.get(Filter.LOGIC);
        Filter.Logic logic = Filter.Logic.and;
        if (logicParam != null) {
            if (logicParam instanceof String) {
                logic = Filter.Logic.valueOf((String) logicParam);
            } else {
                logic = (Logic) logicParam;
            }
        }
        final List filters = (List) filterMap.get(Filter.FILTERS);
        final Filter[] childFilters = new Filter[filters.size()];
        for (int i = 0; i < filters.size(); i++) {
            if (!(filters.get(i) instanceof Map)) {
                throw new BaseAppRuntimeException("json.filter.invalidformat");
            }
            childFilters[i] = this.loadFilter((Map) filters.get(i));
        }
        return new Filter.FilterSuit(logic, childFilters);
    }
    
    /**
     * 判断客户端过滤条件是否为嵌套组合查询条件。
     * 
     * @param filter 客户端过滤条件
     * @return 是否为嵌套组合查询条件
     */
    @JsonIgnore
    private boolean isFilterSuit(final Map<String, Object> filter) {
        // 一个完整的filter集合
        if (filter.containsKey(Filter.FILTER) && (filter.size() == 1)) {
            return true;
        }
        // 允许没有logic，默认是 and 逻辑 
        if (filter.containsKey(Filter.FILTERS) && (filter.get(Filter.FILTERS) instanceof List)
                && (filter.size() <= 2)) {
            return true;
        }
        return false;
    }
    
    /**
     * 判断客户端过滤条件是否为基本查询条件。
     * 
     * @param filter 客户端过滤条件
     * @return 是否为基本查询条件
     */
    @JsonIgnore
    private boolean isFilterLeaf(final Map<String, Object> filter) {
        if (filter.containsKey(Filter.FIELD) && filter.containsKey(Filter.OPERATOR)) {
            return true;
        }
        return false;
    }
}
