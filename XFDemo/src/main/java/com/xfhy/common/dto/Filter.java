package com.xfhy.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.Assert;

/**
 * 查询过滤条件。
 * 
 * @author liuyg
 * @version 1.0
 */
public abstract class Filter {
    
    public static final String FIELD = "field";
    public static final String OPERATOR = "operator";
    public static final String VALUE = "value";
    public static final String FILTERS = "filters";
    public static final String FILTER = "filter";
    public static final String LOGIC = "logic";
    
    /**
     * 是否为过滤条件（叶子节点）。
     * 
     * @return 是否过滤条件
     */
    public abstract boolean isCondition();
    
    /**
     * 过滤条件是否为空。
     * 
     * @return 过滤条件是否为空
     */
    @JsonIgnore
    public abstract boolean isEmpty();
    
    /**
     * 取字段的所有过滤条件。
     * 
     * @param field 字段名
     * @param conditions 过滤条件列表
     */
    public List<Condition> getConditions(final String field) {
        final List<Condition> conditions = new ArrayList<Condition>();
        this.fetchFieldConditions(field, conditions);
        return conditions;
    }
    
    /**
     * 只取字段属性的第一个过滤条件。
     * 
     * @param field 字段名
     * @return 第一个过滤条件
     */
    public abstract Condition getFirstOneCondition(String field);
    
    /**
     * 取字段属性的条件值（同一个字段有多个并列条件的，默认只返回第一个条件）。
     * 
     * @param field
     * @return
     */
    public abstract String getValue(String field);
    
    /**
     * 取字段属性过滤条件的非空值。
     * 
     * @return 过滤条件的非空值
     * @throws RuntimeException 值为空的抛系统异常
     */
    public abstract String getNotBlankValue(String field);
    
    /**
     * 判断是否包含指定字段的过滤条件。
     * 
     * @param field 字段名
     * @return 是否包含指定字段的过滤条件
     */
    public abstract boolean containsField(String field);
    
    /**
     * 取属性字段的过滤条件列表。(一个字段可能有多个过滤条件，该方法无法获取条件间的逻辑关系。如要获取逻辑关系，请使用{FilterSuit#
     * getLogic()})
     * 
     * @param field 字段名
     * @param conditions 过滤条件
     */
    protected abstract void fetchFieldConditions(String field, List<Condition> conditions);
    
    /**
     * 过滤条件的组合对象。
     */
    public static class FilterSuit extends Filter {
        /** 逻辑符号 */
        private final Logic logic;
        /** 过滤条件集合 */
        private final Filter[] filters;
        
        /**
         * 过滤条件组合对象的构造函数。
         * 
         * @param logic 条件集合间的逻辑关系
         * @param filters 条件集合
         */
        public FilterSuit(final Logic logic, final Filter... filters) {
            this.logic = logic;
            this.filters = filters;
        }
        
        /**
         * 取逻辑关系
         * 
         * @return 逻辑关系
         */
        public Logic getLogic() {
            return this.logic;
        }
        
        /**
         * 取过滤条件集合
         * 
         * @return
         */
        public Filter[] getFilters() {
            return this.filters;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#isCondition()
         */
        @Override
        @JsonIgnore
        public boolean isCondition() {
            return false;
        }
        
        /**
         * 获取指定字段的过滤条件。
         * 
         * @param field 字段名
         * @param conditions 过滤条件集合
         */
        @Override
        @JsonIgnore
        public void fetchFieldConditions(final String field, final List<Condition> conditions) {
            if (this.filters == null) {
                return;
            }
            for (final Filter filter : this.filters) {
                filter.fetchFieldConditions(field, conditions);
            }
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#containsField(java.lang.String)
         */
        @Override
        @JsonIgnore
        public boolean containsField(final String field) {
            if (this.isEmpty()) {
                return false;
            }
            for (final Filter filter : this.filters) {
                if (filter.containsField(field)) {
                    return true;
                }
            }
            return false;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#isEmpty()
         */
        @Override
        @JsonIgnore
        public boolean isEmpty() {
            if ((this.filters == null) || (this.filters.length == 0)) {
                return true;
            }
            for (final Filter filter : this.filters) {
                if (!filter.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        
        /*
         * (non-Javadoc)
         * @see
         * cn.com.dhc.common.dto.Filter#getFirstOneCondition(java.lang.String)
         */
        @Override
        @JsonIgnore
        public Condition getFirstOneCondition(final String field) {
            if (this.isEmpty()) {
                return null;
            }
            for (final Filter filter : this.filters) {
                final Condition first = filter.getFirstOneCondition(field);
                if (first != null) {
                    return first;
                }
            }
            return null;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#getValue(java.lang.String)
         */
        @Override
        public String getValue(final String field) {
            final Condition firstCondition = this.getFirstOneCondition(field);
            if (firstCondition != null) {
                String value = firstCondition.getValue();
                if (StringUtils.isBlank(value)) {
                    value = null;
                }
                return value;
            } else {
                return null;
            }
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#getNotBlankValue()
         */
        @Override
        public String getNotBlankValue(final String field) {
            final String value = this.getValue(field);
            Assert.notNull(value);
            return value;
        }
    }
    
    /**
     * 属性字段的过滤条件。
     */
    public static class Condition extends Filter {
        /** 字段名 */
        private final String field;
        /** 操作符 */
        private final Operator operator;
        /** 参数值 */
        private String value;
        
        /**
         * 构造函数。
         * 
         * @param field 字段名
         * @param operator 操作符
         * @param value 参数值
         */
        public Condition(final String field, final Operator operator, final Object value) {
            this.field = field;
            this.operator = operator;
            if ((value != null) && StringUtils.isNotBlank(String.valueOf(value))) {
                this.value = String.valueOf(value);
            }
        }
        
        /**
         * @return
         */
        public String getField() {
            return this.field;
        }
        
        /**
         * @return
         */
        public Operator getOperator() {
            return this.operator;
        }
        
        /**
         * @return
         */
        public String getValue() {
            return this.value;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#isCondition()
         */
        @Override
        @JsonIgnore
        public boolean isCondition() {
            return true;
        }
        
        /*
         * (non-Javadoc)
         * @see
         * cn.com.dhc.common.dto.Filter#fetchFieldConditions(java.lang.String,
         * java.util.List)
         */
        @Override
        @JsonIgnore
        public void fetchFieldConditions(final String field, final List<Condition> conditions) {
            if (this.field.equals(field)) {
                conditions.add(this);
            }
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#containsField(java.lang.String)
         */
        @Override
        @JsonIgnore
        public boolean containsField(final String field) {
            if (this.field.equals(field) && !this.isEmpty()) {
                return true;
            }
            return false;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#isEmpty()
         */
        @Override
        @JsonIgnore
        public boolean isEmpty() {
            return (this.value == null) || "".equals(this.value.trim());
        }
        
        /*
         * (non-Javadoc)
         * @see
         * cn.com.dhc.common.dto.Filter#getFirstOneCondition(java.lang.String)
         */
        @Override
        @JsonIgnore
        public Condition getFirstOneCondition(final String field) {
            if (this.field.equals(field)) {
                return this;
            }
            return null;
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#getValue(java.lang.String)
         */
        @Override
        public String getValue(final String field) {
            if (this.field.equals(field)) {
                return this.getValue();
            } else {
                return null;
            }
        }
        
        /*
         * (non-Javadoc)
         * @see cn.com.dhc.common.dto.Filter#getNotBlankValue(java.lang.String)
         */
        @Override
        public String getNotBlankValue(final String field) {
            final String value = this.getValue(field);
            Assert.notNull(value);
            return value;
        }
    }
    
    /**
     * 操作符
     */
    public static enum Operator {
        eq, // =
        neq, // !=
        gt, // >
        lt, // <
        gte, // >=
        lte, // <=
        startswith, // starts with
        endswith, // end with
        contains; // contains
    }
    
    /**
     * 逻辑符号：逻辑与、逻辑或
     */
    public static enum Logic {
        and, or
    }
}
