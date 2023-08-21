package com.java.dailyTasks.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.dailyTasks.DTO.UserDTO;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.Role;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.domain.enums.RoleType;
import com.java.dailyTasks.exception.message.ErrorMessage;
import com.java.dailyTasks.exceptions.ConflictException;
import com.java.dailyTasks.exceptions.ResourceNotFoundException;
import com.java.dailyTasks.mapper.UserMapper;
import com.java.dailyTasks.repository.UserRepository;
import com.java.dailyTasks.request.RegisterRequest;
import com.java.dailyTasks.request.UserRequest;


@Service
public class UserService {
	
	private UserRepository userRepository;
	
	private ImageService imageService;
	
	private UserMapper userMapper;
	
	private RoleService roleService;
		
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository
			           ,ImageService imageService
			           ,UserMapper userMapper
			           ,RoleService roleService
			           ,PasswordEncoder passwordEncoder) {
		
		this.userRepository=userRepository;
		this.imageService=imageService;
		this.userMapper=userMapper;
		this.roleService=roleService;
		this.passwordEncoder=passwordEncoder;
		
		
	}
	
	
	
	
	
	
	//------------------  get current user login------------------------
	public Optional<String> getCurrentUserLogin(){
		
	SecurityContext securityContext= SecurityContextHolder.getContext();
Authentication authentication=	securityContext.getAuthentication();

    return Optional.ofNullable(extractPricipal(authentication));
		
	}
	//------------------  get principal ------------------------
	private static String extractPricipal(Authentication authentication) {
		
		if (authentication==null) {
			
			return null;
			
		}else if (authentication.getPrincipal() instanceof UserDetails ) {
			
			UserDetails secureUser=(UserDetails) authentication.getPrincipal();
			return secureUser.getUsername();
			
		}else if (authentication.getPrincipal() instanceof String) {
			
			return (String) authentication.getPrincipal();
			
		}
		return null;
		
		
		
	}
	
	//------------------  get current user ------------------------
   public Optional<User> getCurrentUser() {
		
		String email = getCurrentUserLogin().orElseThrow(()->
		 new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
		Optional<User> user =  getUserByEmail(email);
		
		return user ;
		
	}
 //------------------  get current userDTO ------------------------
   public UserDTO getPrincipal() {
	 User currentUser =  getCurrentUser().orElseThrow(()->
	 new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
	  UserDTO userDTO = userMapper.userToUserDto(currentUser);
	  return userDTO;

	
}
	
	
	
// -------------------  get user by id --------------
	public UserDTO getUserById(Long id) {
		
	User user =	userRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,id)));
		
UserDTO userDTO =	userMapper.userToUserDto(user);
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
	List <UserDTO> userDTOList	=userMapper.userToUserDTOList(userList);
		return userDTOList;
	}
	
	
//save user
	public void createUser(UserRequest userRequest, String imageId) {


UserDTO userDTO	=getUserById(userRequest.getId());
	
	User	user=userMapper.userDTOToUser(userDTO);
	
	
	Image imageFile =imageService.findImageByImageId(imageId);
//	
//	Integer usedUserImageCount= userRepository.findCountingById(imageFile);
//		
//	if (usedUserImageCount > 0) {
//		throw new ResourceNotFoundException(ErrorMessage.IMAGE_USED_MESSAGE);
//	}
	
		Set<Image> image = new HashSet<>();
     	image.add(imageFile);
		
		user.setImage(image);
		
		userRepository.save(user);
		
	}
	//update user
	public UserDTO updateUser(String imageId, UserRequest userRequest) {

User user=userMapper.userRequestToUser(userRequest);
		

       if ((user==null)) {
    		new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));
	}
       Image imageFile =imageService.findImageByImageId(imageId);
   
      
        Set<Image> image = new HashSet<>();
        image.add(imageFile);
        user.setImage( image);
       
	 userRepository.save(user);
	         
	   UserDTO userDTO =  userMapper.userToUserDto(user);
	
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
	public void saveUser(String imageId, RegisterRequest registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,registerRequest.getEmail()));
		}
		
	Image img= imageService.findImageByImageId(imageId);
	

	Integer imageCountCheck = userRepository.findUserCountByImageId(img.getId());

	if (imageCountCheck > 0) {
		throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
	}
	
	Set<Image> image=new HashSet<>();
	image.add(img);		
		Role role = roleService.findByType(RoleType.ROLE_ANONYMOUS);
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		String encodedPassword =  passwordEncoder.encode(registerRequest.getPassword());

		
//	User user=	userMapper.registerUserToUser(registerRequest);
		User user = new User();
		user.setImage(image);
		user.setRoles(roles);
		user.setPassword(encodedPassword);
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
//		user.setAddress(registerRequest.getAddress());
////		user.setCreateAt(LocalDateTime.now());
////		user.setUpdateAt(registerRequest.getUpdateAt());
		
//		
		userRepository.save(user);
		
	}
	
		
	

	public void deleteUserWithId(Long id) {
		
		userRepository.deleteById(id);
		
	}

//------------- find user by email-------------------

	public Optional<User> getUserByEmail(String email ) {
		
		  Optional<User> user  =  userRepository.findByEmail(email);
		return user ;
	}

	
	//------------ get image by string id ------------------  extra
//public Image getImage (String id) {
//	Image imageFile =imageService.findImageByImageId(id);
//	return imageFile;
//}
//	
	}
	


