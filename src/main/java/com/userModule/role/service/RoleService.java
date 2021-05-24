package com.userModule.role.service;
 
import java.util.ArrayList;
import java.util.List;
 
import javax.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.userModule.role.model.Role;
import com.userModule.role.repository.RoleRepository;
import com.userModule.user.model.User;
import com.userModule.user.repository.UserRepository;
 
@Service
@Transactional
public class RoleService {
 
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired  
    JdbcTemplate jdbc;
     
    public List<Role> listAllRoles() {
        //return roleRepo.findAll();
    	List<Role> roles = new ArrayList<Role>();  
    	roleRepo.findAll().forEach(role -> roles.add(role));  
    	return roles;  
    }
     
    public void addRole(Role role)  {    
    	roleRepo.save(role);    
    }
     
    public Role getRoleById(long id) {
        return roleRepo.findById(id).get();
    }
     
    public void deleteRoleById(long id) {
    	roleRepo.deleteById(id);
    }
}
