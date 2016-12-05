package com.xfhy.common.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xfhy.common.dto.PagingFilterDTO;
import com.xfhy.common.dto.UserDTO;

public interface UserService {
public UserDTO create(UserDTO userDTO);
	
	public UserDTO update(UserDTO userDTO);
	
	public UserDTO show(String id);
	
	public UserDTO changePass(UserDTO userDTO);
	
	public UserDTO changeUserPass(UserDTO userDTO);
	
	public UserDTO changeUserInfo(UserDTO userDTO);
	
	public UserDTO getLoginUserInfo(String userId);
	
	public boolean auth(UserDTO userDTO);
	
	public boolean autoLogin(UserDTO userDTO);
	
	public List<UserDTO> list();
	
	public Page<UserDTO> list(PagingFilterDTO pagingFilterDTO ) ;
	
	public void updateAvatar(String userId, String avatarId);

	public UserDTO getTokenUser(String tokenId);
	
	public UserDTO apiAuth(UserDTO userDTO);
	
	public UserDTO authByToken(String tokenId);
	
	public boolean logOut(String tokenId);

}
