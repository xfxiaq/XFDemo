package com.xfhy.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.xfhy.common.exception.BaseAppRuntimeException;






public class RequestContextUtils {
	
	private static final Log LOGGER = LogFactory.getLog(RequestContextUtils.class);
	
    /**
     * 取Session中的属性值。
     * 
     * @param key 属性Key
     * @return 属性值
     */
    public static Object getSessionAttribute(final String key) {
        final String sessionKey = RequestContextUtils.getSessionKey(key);
        return RequestContextUtils.getRequest().getAttribute(sessionKey, RequestAttributes.SCOPE_SESSION);
    }
    
    
    /**
     * 往Session中设置属性值。
     * 
     * @param key 属性Key
     * @param value 属性值
     */
    public static void setSessionAttribute(final String key, final Object value) {
        final String sessionKey = RequestContextUtils.getSessionKey(key);
        RequestContextUtils.getRequest().setAttribute(sessionKey, value, RequestAttributes.SCOPE_SESSION);
    }
    
    
    /**
     * 取Session中的属性值。
     * 
     * @param key 属性Key
     * @return 属性值
     */
    public static Object getRequestAttribute(final String key) {
        return RequestContextUtils.getRequest().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }
    
    /**
     * 往Session中设置属性值。
     * 
     * @param key 属性Key
     * @param value 属性值
     */
    public static void setRequestAttribute(final String key, final Object value) {
        RequestContextUtils.getRequest().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
    }
    
    public static boolean hasRequest() {
        return RequestContextHolder.getRequestAttributes() != null;
    }
    
    /**
     * 取Request属性对象。
     * 
     * @return Request属性对象
     * @exception BaseAppRuntimeException 没有Request的情况，抛出系统异常。
     */
    private static RequestAttributes getRequest() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            return RequestContextHolder.getRequestAttributes();
        } else {
            RequestContextUtils.LOGGER.error("Session is not found!");
            throw new BaseAppRuntimeException("Session is not found!");
        }
    }
    
    private static String getSessionKey(final String key) {
    	return key;
    }
    
}
