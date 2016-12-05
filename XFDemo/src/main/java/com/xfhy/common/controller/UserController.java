package com.xfhy.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.UserDTO;
import com.xfhy.common.service.UserService;
import com.xfhy.common.utils.LoginUserUtils;





@Controller
@RequestMapping(value = "/manage/common/user/")
public class UserController  extends AbstractJsonViewController {

	@Resource
	UserService userService;
	
    @RequestMapping(value = "auth_menu_list.json",method = RequestMethod.POST)
    @ResponseBody
	public Page<UserDTO> list(@Valid @RequestBody final PagingFilterDTO pagingFilterDTO){

    	final Page<UserDTO> userList = userService.list(pagingFilterDTO);
    	return userList;
    }
	
    @RequestMapping(value = "/create.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO create(@RequestBody UserDTO userDTO){
    	UserDTO backUserDTO = userService.create(userDTO);
    	
    	return backUserDTO;
	}
    
    @RequestMapping(value = "/update.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO update(@RequestBody UserDTO userDTO){
		
    	UserDTO backUserDTO = userService.update(userDTO);
    	
    	return backUserDTO;
	}
    
    @RequestMapping(value = "show.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO show(@Valid @RequestBody final UserDTO userDTO){
    	return userService.show(userDTO.getId());
	}
    

    @RequestMapping(value = "/changePassword.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO changePassword(@RequestBody UserDTO userDTO){
		
    	UserDTO backUserDTO = userService.changePass(userDTO);
    	
    	return backUserDTO;
	}
    
    /**
     * 管理员为其他用户修改密码
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/changeUserPassword.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO changeUserPassword(@RequestBody UserDTO userDTO){
		
    	UserDTO backUserDTO = userService.changeUserPass(userDTO);
    	
    	return backUserDTO;
	}
    

    @RequestMapping(value = "/getLoginUserInfo.json",method = RequestMethod.POST)
    @ResponseBody
	public UserDTO getLoginUserInfo(){
		// LoginUserUtils.clearSessionLoginUser();
    	UserDTO userDTO = LoginUserUtils.getSessionLoginUser();
    	UserDTO backUserDTO = userService.getLoginUserInfo(userDTO.getId());    	
    	return backUserDTO;
	}
    

    
 
      
   
    
}
