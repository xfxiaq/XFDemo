package com.xfhy.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xfhy.common.dto.TextValueDTO;
import com.xfhy.common.dto.UserDTO;



/**
 * 登录用户工具类。
 * 
 * @author liuyg
 * @version 1.0
 */
@Service
public class LoginUserUtils {
    

	
    /**
     * private constructor
     */
    public LoginUserUtils() {
    }
    
    /**
     * 获取登录用户的Session Key
     * 
     * @param request HTTP请求
     * @return 登录用户的Session Key
     */
    public static String getLoginUserSessionKey() {
        return Constants.LOGIN_USER;
    }
    
    /**
     * 取Session中的登录用户(不建议在Service层调用)。
     * 
     * @return 登录用户
     */
    public static UserDTO getSessionLoginUser() {
        
        if (RequestContextHolder.getRequestAttributes() != null) {
        	UserDTO userDTO = (UserDTO)RequestContextUtils.getSessionAttribute(Constants.LOGIN_USER);
            return userDTO;
        }
        return null;
    }
    public UserDTO getSessionLoginUserSql(){
    	UserDTO userDTO = getSessionLoginUser();
    	return userDTO;
    }
    
  
    
    /**
     * 清空Session中的登录用户(不建议在Service层调用)。
     */
    public static void clearSessionLoginUser() {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            RequestContextUtils.setSessionAttribute(Constants.LOGIN_USER, null);
        }
    }
    
    /**
     * 清空Session中的登录用户［前端APIsession］。
     */
    public static void clearAPISessionLoginUser() {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            RequestContextUtils.setSessionAttribute(Constants.API_USER, null);
        }
    }
    
   
 
    
}
