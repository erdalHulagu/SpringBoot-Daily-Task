package com.java.dailyTasks.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.dailyTasks.DTO.UserDTO;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.Role;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.domain.enums.RoleType;
import com.java.dailyTasks.exceptions.ConflictException;
import com.java.dailyTasks.exceptions.ErrorMessage;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.mapper.UserMapper;
import com.java.dailyTasks.repository.UserRepository;
import com.java.dailyTasks.request.RegisterRequest;
import com.java.dailyTasks.request.UserRequest;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserMapper userMaper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

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
	public void createUser(UserDTO userDto, String imageId) {

	Image imageFile =getImage(imageId);
//	Integer usedUserImageCount= userRepository.findCountingById(imageFile);
//		
//	if (usedUserImageCount > 0) {
//		throw new ResourceNotFoundException(ErrorMessage.IMAGE_USED_MESSAGE);
//	}
	User	user=userMaper.userDTOToUser(userDto);
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

	//---------------- register user----------------------
	public void saveUser(RegisterRequest registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,registerRequest.getEmail()));
		}
		
	Image img= getImage( registerRequest.getImageId());
	Set<Image> image=new HashSet<>();
	image.add(img);		
		Role role = roleService.findByType(RoleType.ROLE_USER);
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		String encodedPassword =  passwordEncoder.encode(registerRequest.getPassword());
		
		User user = new User();
		user.setImage(image);
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodedPassword);
		user.setCreateAt(LocalDateTime.now());
		user.setUpdateAt(registerRequest.getUpdateAt());
		user.setAddress(registerRequest.getAddress());
		user.setRoles(roles);
		
		userRepository.save(user);
		
	}
		
	

	public void deleteUserWithId(Long id) {
		
		userRepository.deleteById(id);
		
	}

//------------- find user by email-------------------
	public Optional<User> getUserByEmail(String email) {
		
        Optional<User> user =	userRepository.findByEmail(email);
		
	if (user.isEmpty()) {
	  throw	new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,email));
		
	}

return user;
	}

	
	//------------ get image by string id ------------------
public Image getImage (String id) {
	Image imageFile =imageService.findImageByImageId(id);
	return imageFile;
}
	
	}
	


