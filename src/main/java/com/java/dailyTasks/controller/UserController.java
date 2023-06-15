package com.java.dailyTasks.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.reguestBody.UserRequest;
import com.java.dailyTasks.response.Response;
import com.java.dailyTasks.response.ResponseMessage;
import com.java.dailyTasks.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		
		User user=userService.getUserById(id);
		
		return ResponseEntity.ok(user);
	
	}
	
//	@GetMapping
//	public ResponseEntity<Page<User>>getAllUser(
//			                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//		                                         @RequestParam(value = "size", required = false, defaultValue = "20") int size,
//				                                 @RequestParam(value = "type", required = false, defaultValue = "ASC") Direction direction
//				){
//     Pageable pageable = PageRequest.of(page, size, Sort.by(direction));
//
//		
//		Page<User> users = userService.getAll(pageable);
//		
//
//		return ResponseEntity.ok(users);
//		
//		
//		
//		
//		
//	}
	
	//get all users
	@GetMapping
	public ResponseEntity<List<User>>getAllUser(){
   

		
		List<User> users = userService.getAll();
		

		return ResponseEntity.ok(users);
		
	}
	// create user
	@PostMapping("/admin")
	
	public ResponseEntity<String> createUser(@Validated  @RequestBody User user){
		userService.createUser(user);
		String message="User created succesfully";
		return ResponseEntity.ok(message);
		
		
	}
	@PutMapping("{id}")
	
	public ResponseEntity<User> upDateUser(@Validated @PathVariable Long id, @RequestBody UserRequest userRequest){
		
		 User updateduser = userService.updateUser(id,userRequest);
		 Response response = new Response();
		 response.setMessage(ResponseMessage.USER_UPDATED_MESSAGE);
		 return ResponseEntity.ok(updateduser);
		
	}
}


















