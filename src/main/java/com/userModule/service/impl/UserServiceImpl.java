package com.userModule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.userModule.dao.UserDao;
import com.userModule.dto.UserRoleDTO;
import com.userModule.model.User;
import com.userModule.repository.UserRepository;
import com.userModule.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
    private UserRepository userRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/*@Autowired
	private UserDao userDao;*/
	
	@Value("${userApplication.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${userApplication.rabbitmq.routingkey}")
	private String routingkey;
     
    public String addUser(UserRoleDTO userRoleDTO)  {  
    	User user = new ModelMapper().map(userRoleDTO, User.class);
    	//userDao.insertUser(user);
    	userRepository.save(user);    
    	LOG.log(Level.INFO, user+" saved successfully");
    	return "Saved Successfully";
    }  
    
    public List<UserRoleDTO> listAllUsers() {
    	LOG.log(Level.INFO, "successfully fetched all");
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
    
    public Map<String, Object> getUser(long id) {
    	return null;
    	//return userDao.getUserById(id);
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
    	System.out.println("Inside send...."+exchange+"..."+routingkey);
    	amqpTemplate.convertAndSend(exchange, routingkey, userRoleDTO);
		System.out.println("Send msg = " + userRoleDTO);
	    
	}
    
    public String streams() {
    	Collection<String> collection = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
    	Stream<String> streamOfCollection = collection.stream();
    	//filtering collection
    	long count = streamOfCollection.filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);
        //filtering and iterating collection
        streamOfCollection
	        .filter(string -> !(string.isEmpty()))  
	        .forEach(string -> System.out.println("forEach"+string+"\n")); 
    	return "success";
    }
    
    public String lambda() {
    	//with type declaration
        MathOperation addition = (int a, int b) -> a + b;
  		
        //with out type declaration
        MathOperation subtraction = (a, b) -> a - b;
  		
        //with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> { return a * b; };
  		
        //without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;
  		
        System.out.println("10 + 5 = " + operate(10, 5, addition));
        System.out.println("10 - 5 = " + operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + operate(10, 5, division));
    	return "success";
    }
    
    interface MathOperation {
	    int operation(int a, int b);
    }
    
    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
    
    public String optional() {
    	//with type declaration
    	Integer value1 = null;
        Integer value2 = new Integer(10);
  		
        //Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);
  		
        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println("SUM:::"+sum(a,b));
    	return "success";
    }
    
    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        //Optional.isPresent - checks the value is present or not
  		
        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());
  		
        //Optional.orElse - returns the value if present otherwise returns
        //the default value passed.
        Integer value1 = a.orElse(new Integer(0));
  		
        //Optional.get - gets the value, value should be present
        Integer value2 = b.get();
        return value1 + value2;
     }
}
