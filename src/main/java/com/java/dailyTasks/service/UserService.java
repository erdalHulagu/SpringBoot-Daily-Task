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
	public void createUser(UserRequest userRequest, String imageId) {

	Image imageFile =getImage(imageId);
//	Integer usedUserImageCount= userRepository.findCountingById(imageFile);
//		
//	if (usedUserImageCount > 0) {
//		throw new ResourceNotFoundException(ErrorMessage.IMAGE_USED_MESSAGE);
//	}
	User	user=userMaper.userRequestToUser(userRequest);
		Set<Image> image = new HashSet<>();
     	image.add(imageFile);
		
		user.setImage(image);
		
		userRepository.save(user);
		
	}
	//update user
	public UserDTO updateUser(String imageId, UserRequest userRequest) {

User user=userMaper.userRequestToUser(userRequest);
		

       if ((user==null)) {
    		new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));
	}
       Image imageFile =getImage(imageId);
   
      
        Set<Image> image = new HashSet<>();
        image.add(imageFile);
        user.setImage(image);
       
	 userRepository.save(user);
	         
	   UserDTO userDTO =  userMaper.userToUserDto(user);
	
	   return userDTO;
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

// email ile userbulma
	public UserDTO getUserByEmail(User emailUser) {
		
		String email =emailUser.getEmail();
		
        User	user =	(userRepository.findByEmail
		                            (email).orElseThrow(
		                                  ()-> new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_NOT_FOUND))));
		
	UserDTO userDTO =	userMaper.userToUserDto(user);

return userDTO;
	}

public Image getImage (String id) {
	Image imageFile =imageService.findImageByImageId(id);
	return imageFile;
}
	
	}
	


