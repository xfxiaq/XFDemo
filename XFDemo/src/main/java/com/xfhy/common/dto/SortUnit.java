package com.xfhy.common.dto;

/**
 * 排序条件。
 * 
 * @author liuyg
 * @version 1.0
 */
public class SortUnit {
    /** 字段名 */
    public String field;
    /** 排序方式：升序、降序 */
    public String dir;
    
    public SortUnit() {
    }
    
    public SortUnit(final String field, final String dir) {
        this.field = field;
        this.dir = dir;
    }
}
