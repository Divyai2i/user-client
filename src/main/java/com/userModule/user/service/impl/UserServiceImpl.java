package com.userModule.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userModule.user.dto.UserRoleDTO;
import com.userModule.user.model.User;
import com.userModule.user.repository.UserRepository;
import com.userModule.user.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	/*@Bean
    public ModelMapper modelMapper() {
       ModelMapper modelMapper = new ModelMapper();
       return modelMapper;
    }*/
	
	private ModelMapper modelMapper = new ModelMapper();
     
    public String addUser(UserRoleDTO userRoleDTO)  {  
    	User user = new ModelMapper().map(userRoleDTO, User.class);
    	userRepository.save(user);    
    	return "Saved Successfully";
    }  
    
    public List<UserRoleDTO> listAllUsers() {
    	return ((List<User>) userRepository.findAll()).stream().map(this::convertToUserLocationDTO)
				.collect(Collectors.toList());
        //return userRepository.findAll();
    	/*List<User> users = new ArrayList<User>();  
    	userRepository.findAll().forEach(user -> users.add(user));  
    	return users;  */
    }
     
    public UserRoleDTO getUserById(long id) {
    	return convertToUserLocationDTO(userRepository.findById(id).get());
    }
     
    public String deleteUserById(long id) {
    	userRepository.deleteById(id);
    	return "Deleted Successfully";
    }
    
    private UserRoleDTO convertToUserLocationDTO(User user) {
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
	    UserRoleDTO userRoleDTO = modelMapper.map(user, UserRoleDTO.class);
		return userRoleDTO;
	}
    
    private UserRoleDTO convertToUserRoleDTOManually(User user) {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		userRoleDTO.setUserId(user.getId());
		userRoleDTO.setFirstName(user.getFirstName());
		userRoleDTO.setLastName(user.getLastName());
		userRoleDTO.setAccount(user.getAccount());
		userRoleDTO.setPassword(user.getPassword());
		userRoleDTO.setEmail(user.getEmail());
		/*Location location = user.getLocation();
		userLocationDTO.setLat(location.getLat());
		userLocationDTO.setLng(location.getLng());
		userLocationDTO.setPlace(location.getPlace());*/
		return userRoleDTO;
	}
}
