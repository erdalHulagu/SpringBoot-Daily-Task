package com.java.dailyTasks.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.exceptions.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.reguestBody.UserRequest;
import com.java.dailyTasks.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User getUserById(Long id) {
		
	User user =	userRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));
		
		return user;
		
		
		
	}
//
//	public Page<User> getAll(Pageable pageable) {
//
//		Page<User> userPage = userRepository.findAll(pageable);
//		
//		
//		return userPage;
//	}
	
	
// get all users
	public List<User> getAll() {
		List<User> userPage = userRepository.findAll();
		return userPage;
	}
	
	
//save user
	public void createUser( User user) {
		
		userRepository.save(user);
		
	}
	//update user
	public User updateUser(Long id, UserRequest userRequest) {
	User user =	getUserById(id);
       
       if ((user==null)) {
    		new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));
	}
         

	
	user.setFirstName(userRequest.getFirstName());
	user.setLastName(userRequest.getLastName());
	user.setCity(userRequest.getCity());
	user.setState(userRequest.getState());
	user.setEmail(userRequest.getEmail());
	
	userRepository.save(user);
	
	return user;
	}
//	private Optional<User> getUserByEmail(String email) {
//		
//
//		  User user  =  userRepository.findByEmail(email).orElseThrow(()->
//		  			new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email))
//				);
//		return Optional.of((user)) ;
//		
//	}
	

}
