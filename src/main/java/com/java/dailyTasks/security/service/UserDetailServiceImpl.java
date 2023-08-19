package com.java.dailyTasks.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.exception.message.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.service.UserService;


// UserDetailsService classina user repositoriden findby name iile kendi userimizi yani kim login olacaksa onu getirdik 
@Component
public class UserDetailServiceImpl implements UserDetailsService{
	

    @Autowired
    private UserService userService;
    

	@Override
	public UserDetails loadUserByUsername(String  email) throws UsernameNotFoundException {
		
		Optional< User> user =  userService.getUserByEmail(email);
		 return user.map(UserDetailImpl::new)
	                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_NOT_FOUND, email)));
		 
		 
//	    user.map(t->new UserDetailImpl(t)).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_NOT_FOUND, email)));  solede kullanabiliriz

	    }
	
    
}		
		
		       
		
		
	



