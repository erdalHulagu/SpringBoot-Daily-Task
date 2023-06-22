package com.java.dailyTasks.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.dailyTasks.DTO.UserDTO;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.exceptions.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.mapper.UserMapper;
import com.java.dailyTasks.repository.UserRepository;
import com.java.dailyTasks.request.UserRequest;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserMapper userMaper;
	
	

	public UserDTO getUserById(Long id) {
		
	User user =	userRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));
		
UserDTO userDTO =	userMaper.userToUserDto(user);
		return userDTO;
		
		
		
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
	public List<UserDTO> getAllUsers() {
		List<User> userList = userRepository.findAll();
		
		if (userList.isEmpty()) {
			new ResourceNotFoundException(String.format(ErrorMessage.USER_LIST_IS_EMPTY));
		}
	List <UserDTO> userDTOList	=userMaper.userToUserDTOList(userList);
		return userDTOList;
	}
	
	
//save user
	public void createUser(UserDTO userDTO, Long imageId) {

		User	user=userMaper.userDTOToUser(userDTO);
	
	Image imageFile =imageService.findImageById(imageId);
		
		Set<Image> image = new HashSet<>();
     	image.add(imageFile);
		
		user.setImage(image);
		
		userRepository.save(user);
		
	}
	//update user
	public UserDTO updateUser(Long id, UserRequest userRequest) {

User user=	userRepository.findByEmail(userRequest.getEmail()).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_NOT_FOUND)));
       
       if ((user==null)) {
    		new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));
	}
       
        User user1 = userMaper.userRequestToUser(userRequest);
	     User  user2 =userRepository.save(user1);
	         
	   UserDTO userDTO2 =  userMaper.userToUserDto(user2);
	
	   return userDTO2;
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




	public void deleteUserWithId(Long id) {
		
		userRepository.deleteById(id);
		
	}


	public UserDTO getUserByEmail(User emailUser) {
		
		String email =emailUser.getEmail();
		
        User	user =	(userRepository.findByEmail
		                            (email).orElseThrow(
		                                  ()-> new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_NOT_FOUND))));
		
	UserDTO userDTO =	userMaper.userToUserDto(user);

return userDTO;
	}


	
	}
	


