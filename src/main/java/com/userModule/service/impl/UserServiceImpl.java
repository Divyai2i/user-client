package com.userModule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userModule.dto.UserRoleDTO;
import com.userModule.model.User;
import com.userModule.repository.UserRepository;
import com.userModule.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${userApplication.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${userApplication.rabbitmq.routingkey}")
	private String routingkey;
     
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
		return userRoleDTO;
	}
    
    public void send(UserRoleDTO userRoleDTO) {
    	System.out.println("Inside send...."+exchange+"..."+exchange);
    	amqpTemplate.convertAndSend(exchange, routingkey, userRoleDTO);
		System.out.println("Send msg = " + userRoleDTO);
	    
	}
}
