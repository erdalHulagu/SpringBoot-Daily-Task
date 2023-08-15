package com.java.dailyTasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dailyTasks.domain.Role;
import com.java.dailyTasks.domain.enums.RoleType;
import com.java.dailyTasks.exception.message.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.repository.RoleRepository;


@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByType(RoleType roleType) {
		Role role =  roleRepository.findByType(roleType).orElseThrow(()->
		       new ResourceNotFoundException(String.format(
		    		   ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name())));
		
		return role ;
		
	}

}
