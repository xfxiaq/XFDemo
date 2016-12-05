package com.xfhy.common.model;

/**
 * ID、Version 接口
 * 
 * @author zxl
 * @version 1.0
 */
public interface IdVersion {
    /**
     * 取Id
     * 
     * @return Id
     */
    String getId();
    
    /**
     * 取版本号
     * 
     * @return 版本号
     */
    Long getVersion();
}
