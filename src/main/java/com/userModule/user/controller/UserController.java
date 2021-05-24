package com.userModule.user.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.userModule.MethodExecutionTime;
import com.userModule.user.controller.FeignProxy;
import com.userModule.user.dto.UserRoleDTO;
import com.userModule.user.service.UserService;

@RestController  
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	FeignProxy proxyService;
	
	@RequestMapping("/dashboard/feign/{id}")
    public UserRoleDTO findById(@PathVariable Long id){
       return proxyService.findById(id);
    }
	
	@RequestMapping("/dashboard/feign/peers")
    public  Collection<UserRoleDTO> findAll(){
        return proxyService.findAll();
    }
	
	@MethodExecutionTime
	@PostMapping   
	public String addUser(@RequestBody UserRoleDTO userRoleDTO)  
	{    
		return userService.addUser(userRoleDTO);    
	}    
	
	@MethodExecutionTime
	@Cacheable(value = "allusers")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<UserRoleDTO> listAllUsers() {
	    return userService.listAllUsers();
	}
	
	@MethodExecutionTime
	@Cacheable(value = "users", key = "#id")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public UserRoleDTO getUserById(@PathVariable("id") long id) {
	    return userService.getUserById(id);
	}
	
	@MethodExecutionTime
	//@CacheEvict(value = "users", allEntries=true)
	@CacheEvict(value = "users", key = "#id")
	@DeleteMapping("/{id}")
	public String deleteUserById(@PathVariable("id") long id) {
	    return userService.deleteUserById(id);
	}
	
	@MethodExecutionTime
	@CachePut(value = "users", key = "#id")
	@PutMapping("/{id}")
	public String updateUserById(@RequestBody UserRoleDTO userRoleDTO, @PathVariable("id") long id) {
		UserRoleDTO userRoleDTOOld = userService.getUserById(id);
		userRoleDTOOld.setFirstName(userRoleDTO.getFirstName());
		userRoleDTOOld.setLastName(userRoleDTO.getLastName());
		userRoleDTOOld.setAccount(userRoleDTO.getAccount());
		userRoleDTOOld.setPassword(userRoleDTO.getPassword());
		userRoleDTOOld.setEmail(userRoleDTO.getEmail());
	    userService.addUser(userRoleDTOOld);
	    return "Updated successfully";
	}
}

