package com.xfhy.common.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理器
 * 
 * @author zxl
 */
public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
    /** logger */
    private static final Log Logger = LogFactory.getLog(CustomMappingExceptionResolver.class);
    /** 本地消息源 */
    @Resource
    private MessageSource messageSource;
    
    /** 异常解析器列表 */
    private List<RequestTypeExceptionResolver> exceptionResolvers;
    
    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
     * #doResolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object,
     * java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler, final Exception ex) {
        // 优先处理XML处配置的异常处理
        final String viewName = this.determineViewName(ex, request);
        Integer statusCode = null;
        if ((viewName != null) && ((statusCode = this.determineStatusCode(request, viewName)) != null)) { // 优先处理XML处配置的异常处理
            this.applyStatusCodeIfPossible(request, response, statusCode);
            return this.getModelAndView(viewName, ex, request);
        }
        for (final RequestTypeExceptionResolver resolver : this.getExceptionResolvers()) {
            if (resolver.supportsRequest(request)) {
                return resolver.resolveException(request, response, handler, ex);
            }
        }
        if (CustomMappingExceptionResolver.Logger.isErrorEnabled()) {
            CustomMappingExceptionResolver.Logger.error("there's no exception resolver found!", ex);
        }
        return null;
    }
    
    /**
     * This method should only be used by unit tests.
     * 
     * @param messageSource
     */
    protected void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public List<RequestTypeExceptionResolver> getExceptionResolvers() {
        if (this.exceptionResolvers == null) {
            this.exceptionResolvers = this.getDefaultExceptionResolvers();
        }
        return this.exceptionResolvers;
    }
    
    /**
     * 设置用户自定义的异常解析器。
     * 
     * @param exceptionResolvers 异常解析器列表
     */
    public void setCustomExceptionResolvers(final List<RequestTypeExceptionResolver> exceptionResolvers) {
        this.exceptionResolvers = exceptionResolvers;
    }
    
    /**
     * 获取默认的异常处理器集合。 <li>AJAX 请求的异常处理器 <li>JSP 请求的异常处理器
     * 
     * @return 异常处理器集合
     */
    protected List<RequestTypeExceptionResolver> getDefaultExceptionResolvers() {
        final List<RequestTypeExceptionResolver> exceptionResolvers = new ArrayList<RequestTypeExceptionResolver>();
//        final AjaxMappingExceptionResolver ajaxResolver = new AjaxMappingExceptionResolver();
//        ajaxResolver.setMessageSource(this.messageSource);
//        final JspMappingExceptionResolver jspResolver = new JspMappingExceptionResolver();
//        jspResolver.setMessageSource(this.messageSource);
//        exceptionResolvers.add(ajaxResolver);
//        exceptionResolvers.add(jspResolver);
        return exceptionResolvers;
    }
}