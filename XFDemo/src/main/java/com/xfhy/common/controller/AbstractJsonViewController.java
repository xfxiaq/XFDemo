package com.xfhy.common.controller;


import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;



/**
 * 抽象的 JSON 视图控制器类.
 * 
 * @author zxl
 * @version 1.0
 */
public abstract class AbstractJsonViewController {
    
    /** logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonViewController.class);
    
    /** 国际化消息源. */
    @Resource
    protected MessageSource messageSource;
    
    /**
     * 单元测试用的本地化文件。
     * 
     * @param messageSource 本地化文件
     */
    protected void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    /**
     * 取本地化消息。
     * 
     * @param code 消息编码
     * @param params 消息参数
     * @return 本地化消息
     */
    protected String getLocalMessage(final String code, final Object[] params) {
        final Locale current = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(code, params, code, current);
    }
    
    /**
     * 取本地化消息。
     * 
     * @param code 消息编码
     * @return 本地化消息
     */
    protected String getLocalMessage(final String code) {
        return this.getLocalMessage(code, null);
    }
    

}
