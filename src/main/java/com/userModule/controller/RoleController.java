package com.userModule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.userModule.dto.RoleUserDTO;
import com.userModule.service.RoleService;

@RestController  
@RequestMapping("/api/v1/roles")
public class RoleController {
	
	@Autowired  
	RoleService roleService;
	
	@PostMapping    
	public String addRole(@RequestBody RoleUserDTO roleUserDTO)  {    
		return roleService.addRole(roleUserDTO);    
	}    
	
	@GetMapping
	public List<RoleUserDTO> listAllRoles() {
	    return roleService.listAllRoles();
	}
	
	@GetMapping("/{id}")
	public RoleUserDTO getRoleById(@PathVariable("id") long id) {
	    return roleService.getRoleById(id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteRoleById(@PathVariable("id") long id) {
		return roleService.deleteRoleById(id);
	}
	
	@PutMapping("/{id}")
	public String updateRoleById(@RequestBody RoleUserDTO roleUserDTO, @PathVariable("id") long id) {
		RoleUserDTO roleUserDTOOld = roleService.getRoleById(id);
		roleUserDTOOld.setName(roleUserDTO.getName());
		roleUserDTOOld.setDescription(roleUserDTO.getDescription());
		roleService.addRole(roleUserDTOOld);
		return "Updated successfully";
	}
}

