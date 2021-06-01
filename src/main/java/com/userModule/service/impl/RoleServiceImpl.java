package com.userModule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userModule.dto.RoleUserDTO;
import com.userModule.model.Role;
import com.userModule.repository.RoleRepository;
import com.userModule.service.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
     
    public String addRole(RoleUserDTO roleUserDTO)  {  
    	Role role = new ModelMapper().map(roleUserDTO, Role.class);
    	roleRepository.save(role);    
    	return "Role Saved Successfully";
    }  
    
    public List<RoleUserDTO> listAllRoles() {
    	return ((List<Role>) roleRepository.findAll()).stream().map(this::convertToRoleUserDTO)
				.collect(Collectors.toList());
    }
     
    public RoleUserDTO getRoleById(long id) {
    	return convertToRoleUserDTO(roleRepository.findById(id).get());
    }
     
    public String deleteRoleById(long id) {
    	roleRepository.deleteById(id);
    	return "Role Deleted Successfully";
    }
    
    private RoleUserDTO convertToRoleUserDTO(Role role) {
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	    RoleUserDTO roleUserDTO = modelMapper.map(role, RoleUserDTO.class);
		return roleUserDTO;
	}
}
