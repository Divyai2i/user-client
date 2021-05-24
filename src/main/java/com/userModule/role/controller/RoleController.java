package com.userModule.role.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.userModule.role.model.Role;
import com.userModule.role.service.RoleService;
import com.userModule.user.controller.PostMapping;

@RestController  
@RequestMapping("/api/v1/roles")
public class RoleController {
	
	@Autowired  
	RoleService roleService;
	
	@PostMapping    
	public void addRole(@RequestBody Role role)  {    
		roleService.addRole(role);    
	}    
	
	@GetMapping
	public List<Role> list() {
	    return roleService.listAllRoles();
	}
	
	@GetMapping("/{id}")
	public Role getRoleById(@PathVariable("id") long id) {
	    return roleService.getRoleById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteRoleById(@PathVariable("id") long id) {
		roleService.deleteRoleById(id);
	}
	
	@PutMapping("/{id}")
	public void updateRoleById(@RequestBody Role role, @PathVariable("id") long id) {
		Role roleOld = roleService.getRoleById(id);
		roleOld.setName(role.getName());
		roleOld.setDescription(role.getDescription());
		roleService.addRole(roleOld);
	}
}

