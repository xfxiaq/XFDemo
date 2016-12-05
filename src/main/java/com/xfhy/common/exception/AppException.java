package com.xfhy.common.exception;

/**
 * 业务异常接口类。
 * 
 * @author zxl
 * @version 1.0
 */
public interface AppException {
    /**
     * 取业务消息。
     * 
     * @return 业务消息
     */
    public String getMessage();
    
    /**
     * 取消息参数。
     * 
     * @return 消息参数
     */
    public Object[] getMsgParams();
}
