package com.xfhy.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import com.xfhy.common.dto.UserDTO;
import com.xfhy.common.service.UserService;
import com.xfhy.common.utils.Constants;
import com.xfhy.common.utils.LoginUserUtils;
import com.xfhy.common.utils.RequestContextUtils;




@Controller
@RequestMapping(value = "/manage/common/login/")
public class LoginController {

	@Resource
	UserService userService;
	
    @RequestMapping(value = "auth.json",method = RequestMethod.POST)
    @ResponseBody    
    public Map<String,String> auth(@Valid @RequestBody final UserDTO userDTO){
    	Map<String,String> result = new HashMap<String, String>();
    	boolean auth = userService.auth(  userDTO);
    	if(auth){
    		userDTO.set_backcode(new Date().toString());
    		RequestContextUtils.setSessionAttribute(Constants.LOGIN_USER, userDTO);
    		result.put("result", "true");
    		result.put("key", userDTO.getPassword());
    	}else{
    		LoginUserUtils.clearSessionLoginUser();
    		result.put("result", "false");
    		result.put("key", "");
    	}
    	return result;
	}
    
    @RequestMapping(value = "autoLogin.json",method = RequestMethod.POST)
    @ResponseBody    
    public boolean autoLogin( @RequestBody final UserDTO userDTO){

    	boolean auth = userService.autoLogin(userDTO);
    	if(auth){
    		userDTO.set_backcode(new Date().toString());
    		RequestContextUtils.setSessionAttribute(Constants.LOGIN_USER, userDTO);
    		return true;
    	}else{
    		LoginUserUtils.clearSessionLoginUser();
    		return false;
    	}
	}
    
    @RequestMapping(value = "logout.json",method = RequestMethod.POST)
    @ResponseBody    
    public boolean logout(){
    	LoginUserUtils.clearSessionLoginUser();
    	return true;
	}
    
    @RequestMapping(value = "keepSession",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkAppVersion(){
    	LoginUserUtils.getSessionLoginUser();
    	return true;
	}
    
    /**
	 * 获取session值
	 * @return
	 */

//	@RequestMapping(value = "/getSession.json",method = RequestMethod.POST)
//    @ResponseBody
//	public Map<String ,String> getSession(HttpServletRequest request){
//		
//		Map<String,String> registrationMap = new HashMap<String,String>();
//		String appId = WxConfig.getAPPID();
//		if (RequestContextHolder.getRequestAttributes() != null) {
//        	UserDTO userDTO = (UserDTO)RequestContextUtils.getSessionAttribute(Constants.LOGIN_USER);
//        	registrationMap.put("id", userDTO.getId());
//        }
//		registrationMap.put("appId",appId);					
//		return registrationMap;
//		
//	}
    
}
