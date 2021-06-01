package com.userModule.service;

import java.util.List;

import com.userModule.dto.RoleUserDTO;

public interface RoleService {
	
	public String addRole(RoleUserDTO roleUserDTO);
	
	public List<RoleUserDTO> listAllRoles();
	
	public RoleUserDTO getRoleById(long id);
	
	public String deleteRoleById(long id);
     
}
