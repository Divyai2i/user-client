package com.userModule.service;
 
import java.util.List;

import com.userModule.dto.UserRoleDTO;

public interface UserService {
	
	public String addUser(UserRoleDTO userRoleDTO);
	
	public List<UserRoleDTO> listAllUsers();
	
	public UserRoleDTO getUserById(long id);
	
	public String deleteUserById(long id);
	
	public void send(UserRoleDTO userRoleDTO);
     
}
