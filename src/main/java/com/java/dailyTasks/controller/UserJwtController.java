package com.java.dailyTasks.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.dailyTasks.request.LoginRequest;
import com.java.dailyTasks.request.RegisterRequest;
import com.java.dailyTasks.response.LoginResponse;
import com.java.dailyTasks.response.Response;
import com.java.dailyTasks.response.ResponseMessage;
import com.java.dailyTasks.security.jwt.JwtUtils;
import com.java.dailyTasks.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
public class UserJwtController {
	
	// Bu classımda sadece login ve register işlemleri yapılacak
	
   @Autowired
   private UserService userService;
   
   @Autowired
   private AuthenticationManager authenticationManager;
   
   @Autowired
   private JwtUtils  jwtUtils;
   
   
   // register
  
//   @PostMapping("/register/{imageId}")
   @PostMapping("/register/{imageId}")
   @Transactional
   public ResponseEntity<Response> registerUser(@PathVariable String imageId , @Valid @RequestBody RegisterRequest registerRequest  )  {
	   userService.saveUser(imageId,registerRequest);
	   
	   Response response = new Response();
	   response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
	   response.setSuccess(true);
	   
	   return new ResponseEntity<>(response,HttpStatus.CREATED);
  
   }

   
   
   // login
   @PostMapping("/login")
//   @Transactional
   public ResponseEntity<LoginResponse> authenticate( @Validated @RequestBody LoginRequest loginRequest    )  {
	   
	   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
			     new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
	   
	    Authentication authentication  =  authenticationManager.
	    		       authenticate(usernamePasswordAuthenticationToken);
	    
	 UserDetails userDetails  =  (UserDetails) authentication.getPrincipal() ;// mevcut giriş yapan kullanıcıyı getirir
	    
	   String jwtToken =   jwtUtils.generateJwtToken(userDetails);
	   
	   LoginResponse loginResponse = new LoginResponse(jwtToken);
	   
	   return new ResponseEntity<>(loginResponse,HttpStatus.OK);
	   
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
