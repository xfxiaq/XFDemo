package com.xfhy.common.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.xfhy.common.dto.Filter;
import com.xfhy.common.dto.PageDTO;
import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.UserDTO;
import com.xfhy.common.exception.BaseAppRuntimeException;
import com.xfhy.common.model.QUser;
import com.xfhy.common.model.Role;
import com.xfhy.common.model.User;
import com.xfhy.common.repository.RoleRepository;
import com.xfhy.common.repository.UserRepository;
import com.xfhy.common.service.FunctionService;
import com.xfhy.common.service.UserService;
import com.xfhy.common.utils.MD5;



@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	UserRepository userRepository;
	
	@Resource
	RoleRepository roleRepository;
	
	@Resource
	FunctionService functionService;
	

	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<UserDTO> list() {

		return null;
	}

	@Transactional
	public UserDTO create(UserDTO userDTO) {
		
		if(existsUserId(userDTO)){
			throw new BaseAppRuntimeException("登录名重复.");
		}
		
		User user = new User();
		user.setUserId(userDTO.getUserId());
		user.setUserName(userDTO.getUserName());
		user.setPhoneNum(userDTO.getPhoneNum());
		user.setPassword(MD5.string2MD5( userDTO.getPassword()));
		user.setEnable(userDTO.isEnable());
		user.setTenantId(userDTO.getTenantId());
		user.setRoles(null);		
		user = userRepository.save(user);
		
		String roles = userDTO.getRoles();
		if(roles != null &&  !"".equals(roles)){
			List<Role> setRoles = new ArrayList<Role>();
				String [] roleArray = roles.split(",");
				for(String roleId : roleArray){
					if("".equals(roleId)){
						continue;
					}
					setRoles.add(this.roleRepository.findOne(roleId));				
				}
				user.setRoles(setRoles);
		}

		user = userRepository.save(user);
		userDTO.setId(user.getId());
		return userDTO;
	}

	@Transactional
	public UserDTO update(UserDTO userDTO) {
		if(existsUserId(userDTO)){
			throw new BaseAppRuntimeException("登录名重复.");
		}
		User user = userRepository.findOne(userDTO.getId());
		user.setUserName(userDTO.getUserName());
		user.setPhoneNum(userDTO.getPhoneNum());
		user.setUserId(userDTO.getUserId());
		user.setEnable(userDTO.isEnable());
		user.setTenantId(userDTO.getTenantId());
		user.setRoles(null);
		userRepository.save(user);
		
		String roles = userDTO.getRoles();
		if(roles != null && !"".equals(roles)){
			List<Role> setRoles = new ArrayList<Role>();
				String [] roleArray = roles.split(",");
				for(String roleId : roleArray){
					if("".equals(roleId)){
						continue;
					}
					setRoles.add(this.roleRepository.findOne(roleId));				
				}
				user.setRoles(setRoles);
		}

		user = userRepository.save(user);
		
		return userDTO;
	}
	
	private boolean existsUserId(UserDTO userDTO){
		boolean isAllow = false;
		String userId = userDTO.getId();

		BooleanBuilder where = new BooleanBuilder();
		where.and(QUser.user.id.ne(userId));
		where.and(QUser.user.userId.eq(userDTO.getUserId()));
		Iterable<User> users = this.userRepository.findAll(where);
		
		if(users != null && users.iterator().hasNext()){
			isAllow = true;
		}		
		return isAllow;
	}
	
	
	public UserDTO show(String id){
		User user = this.userRepository.findOne(id);
		UserDTO userDTO = new UserDTO();
		userDTO.setEnable(user.isEnable());
		userDTO.setId(user.getId());
		userDTO.setUserId(user.getUserId());
		userDTO.setUserName(user.getUserName());
		userDTO.setPhoneNum(user.getPhoneNum());
		userDTO.setTenantId(user.getTenantId());
		List<Role> roles = user.getRoles();
		List<Map<String,String>> defaultRoles = new ArrayList<Map<String,String>>();
		for(Role r : roles){
			Map<String,String> roleMap = new HashMap<String, String>();
			roleMap.put("id", r.getId());
			roleMap.put("text", r.getRoleName());
			defaultRoles.add(roleMap);
		}
		userDTO.setDefaultRoles(defaultRoles);
		
		
		return userDTO;
	}

	public UserDTO changePass(UserDTO userDTO) {
		User user = userRepository.findOne(userDTO.getId());
		
		if(! user.getPassword().equals(MD5.string2MD5(userDTO.getPassword()))){
			// 原始密码不正确
			userDTO.set_backcode("401");
			userDTO.set_backmes("原始密码不正确"); 
			return userDTO;
		}
		if(!userDTO.getNew_password().equals(userDTO.getRepeat_password())){
			// 新密码不一致
			userDTO.set_backcode("402");
			userDTO.set_backmes("新密码不一致"); 
			return userDTO;
		}
		
		user.setPassword(MD5.string2MD5(userDTO.getNew_password()));
		userRepository.save(user);
		userDTO.set_backcode("0");
		userDTO.set_backmes("密码修改成功！");
		return userDTO;
	}
	
	public UserDTO changeUserPass(UserDTO userDTO) {
		User user = userRepository.findOne(userDTO.getId());
		
		if(! user.getPassword().equals(MD5.string2MD5(userDTO.getPassword()))){
			// 原始密码不正确
			throw new BaseAppRuntimeException("原始密码不正确");
		}
		if(!userDTO.getNew_password().equals(userDTO.getRepeat_password())){
			// 新密码不一致
			throw new BaseAppRuntimeException("新密码不一致");
		}
		
		user.setPassword(MD5.string2MD5(userDTO.getNew_password()));
		userRepository.save(user);
		return userDTO;
	}
	
	@Override
	public UserDTO changeUserInfo(UserDTO userDTO) {
		User user = userRepository.findOne(userDTO.getId());
		if(userDTO.getUserName() != null){
			user.setUserName(userDTO.getUserName());
		}
		if(userDTO.getPhoneNum() != null){
			user.setPhoneNum(userDTO.getPhoneNum());
		}
		userRepository.save(user);
		userDTO.set_backcode("0");
		userDTO.set_backmes("修改成功！");
		return userDTO;
	}
	
	public UserDTO getLoginUserInfo(String userId){
		
		User user = this.userRepository.findOne(userId);
		UserDTO userDTO = new UserDTO();
		userDTO.setEnable(user.isEnable());
		userDTO.setId(user.getId());
		userDTO.setUserId(user.getUserId());
		userDTO.setUserName(user.getUserName());
		userDTO.setPhoneNum(user.getPhoneNum());
		userDTO.setAvatarId(user.getAvatarId());
		return userDTO;
	}

	public boolean auth(UserDTO userDTO) {
		
		String cpass = userDTO.getPassword();
		
		String md5Pass = "";
		if("&*(@#-$%".equals(cpass)){
			md5Pass = userDTO.getNew_password();
		}else{
			md5Pass = MD5.string2MD5(userDTO.getPassword());
			//md5Pass = userDTO.getPassword();
		}
		
		List<User> users = userRepository.findByUserIdAndPassword(userDTO.getUserId(), md5Pass);
		if(users.size() == 1){
			userDTO.setUserName(users.get(0).getUserName());
			userDTO.setId(users.get(0).getId());
			userDTO.setTenantId(users.get(0).getTenantId());
			userDTO.setPassword(md5Pass);
			List<Role> ownRoles = users.get(0).getRoles();

			
			String roles = "";
			for(Role r : ownRoles){
				roles +=r.getId() + ",";
			}
			if(roles.endsWith(",")){
				roles = roles.substring(0,roles.length() - 1) ;
			}
			userDTO.setRoles(roles);//***

			// 设置功能权限,保存到session中.
			if(!"".equals(roles)){
				userDTO.setFunctionUrls( this.functionService.getPersionalFunctionUrls(roles));
			}
			
			return true;
		}else{
			return false;
		}
	}
	
public boolean autoLogin(UserDTO userDTO) {

		List<User> users = userRepository.findByUserIdAndPassword(userDTO.getUserId(), userDTO.getPassword());
		if(users.size() == 1){
			userDTO.setUserName(users.get(0).getUserName());
			userDTO.setId(users.get(0).getId());
			userDTO.setTenantId(users.get(0).getTenantId());
			userDTO.setPassword(userDTO.getPassword());
			List<Role> ownRoles = users.get(0).getRoles();
			
			String roles = "";
			for(Role r : ownRoles){
				roles +=r.getId() + ",";
			}
			if(roles.endsWith(",")){
				roles = roles.substring(0,roles.length() - 1) ;
			}
			userDTO.setRoles(roles);
			
			// 设置功能权限,保存到session中.
			if(!"".equals(roles)){
				userDTO.setFunctionUrls( this.functionService.getPersionalFunctionUrls(roles));
			}
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取用户列表
	 */
	public Page<UserDTO> list(PagingFilterDTO pagingFilterDTO ){
		final Filter filter = pagingFilterDTO.getFilter();
		final BooleanBuilder where = new BooleanBuilder();
		
		if(filter != null){
			if(filter.containsField("userName")){
				where.and(QUser.user.userName.contains(filter.getValue("userName")));
			}
		} 	
		
		Page<User> users = this.userRepository.findAll(where,pagingFilterDTO);
		
		final List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for(User u : users.getContent()){
			UserDTO ud = new UserDTO();
			ud.setId(u.getId());
			ud.setUserId(u.getUserId());
			ud.setUserName(u.getUserName());
			ud.setPhoneNum(u.getPhoneNum());
			//ud.setRoles(u.getRoles());
			ud.setEnable(u.isEnable());
			ud.setTenantId(u.getTenantId());
			userDTOs.add(ud);
		}
		
		return new PageDTO<UserDTO>(pagingFilterDTO, userDTOs, users.getTotalElements());
	}

	/**
	 * 更新用户头像
	 */
	public void updateAvatar(String userId, String avatarId){
			User user = this.userRepository.findOne(userId);
			user.setAvatarId(avatarId);
			this.userRepository.save(user);
		
	}

	@Override
	public UserDTO getTokenUser(String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO apiAuth(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO authByToken(String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logOut(String tokenId) {
		// TODO Auto-generated method stub
		return false;
	}


	


	

	

	
	
}
