package com.xfhy.common.exception;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 基本的业务运行时异常。
 * 
 * @author zxl
 * @version 1.0
 */
public class BaseAppRuntimeException extends RuntimeException implements AppException {
    /** 序列号 */
    private static final long serialVersionUID = -6021077900819863433L;
    /** 消息参数 */
    private Object[] msgParams;
    
    /**
     * 空构造函数。
     */
    public BaseAppRuntimeException() {
        super();
    }
    
    /**
     * 构造函数。
     * 
     * @param message 消息
     */
    public BaseAppRuntimeException(final String message) {
        super(message);
    }
    
    /**
     * 构造函数。
     * 
     * @param message 消息
     * @param msgParams 消息参数
     */
    public BaseAppRuntimeException(final String message, final Object[] msgParams) {
        super(message);
        this.msgParams = msgParams;
    }
    
    /**
     * 构造函数。
     * 
     * @param cause 底层异常
     */
    public BaseAppRuntimeException(final Throwable cause) {
        super(cause);
    }
    
    /**
     * 构造函数。
     * 
     * @param message 消息
     * @param cause 底层异常
     */
    public BaseAppRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 构造函数。
     * 
     * @param message 消息
     * @param msgParams 消息参数
     * @param cause 底层异常
     */
    public BaseAppRuntimeException(final String message, final Object[] msgParams, final Throwable cause) {
        this(message, cause);
        this.msgParams = msgParams;
    }
    
    /*
     * (non-Javadoc)
     * @see cn.com.dhc.common.exception.AppException#getMsgParams()
     */
    //@Override  // zxl
    public Object[] getMsgParams() {
        return this.msgParams;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
