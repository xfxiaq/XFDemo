package com.xfhy.common.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 按请求类型划分的异常解析器接口。
 * 
 * @author zxl
 * @version
 */
public interface RequestTypeExceptionResolver extends HandlerExceptionResolver {
    /**
     * 是否为支持的请求。
     * 
     * @param request HTTP请求
     * @return 是否为支持的请求
     */
    public boolean supportsRequest(HttpServletRequest request);
}