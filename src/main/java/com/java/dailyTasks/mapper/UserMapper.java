package com.java.dailyTasks.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.java.dailyTasks.DTO.UserDTO;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.request.UserRequest;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper  {
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "image" , ignore = true )
	User userDTOToUser(UserDTO userDTO);
  
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "image",source = "image",qualifiedByName = "getImageAsLong")
	User userRequestToUser(UserRequest userRequest);
	


	@Mapping(target = "image",source = "image",qualifiedByName = "getImageAsString")
	UserDTO userToUserDto(User user);
	
	
	List<UserDTO> userToUserDTOList(List<User> userList);
	  
	 // long turunde image i image turunde image e cevidik
	 @Named("getImageAsLong")
   public static Set<Image> map(Set<String> imageUrls) {
		 Set<Image> images = new HashSet<>();
	        for (String imageUrl : imageUrls) {
	            Image image = new Image();
	            image.setId(imageUrl);   // Eğer Image sınıfında başka alanlar varsa, diğer alanları da ayarlayabilirsiniz
	            images.add(image);
	        }
	        return images;
	 }
	 @Named("getImageAsString")
		public static  Set<String> getImageIds( Set<Image> imageFiles) {
			Set<String> imgs = new HashSet<>();
			imgs = imageFiles.stream().map(imFile->imFile.getId().
																	toString()).
																	collect(Collectors.toSet());
			 return imgs;
		}
//
//	 @Named("getImageAsStringForRequset")
//	 public static  Set<Image> getImage( Set<String> imageFiles) {
//			Set<Image> imgs = new HashSet<>();
//			imgs = imageFiles.stream().map(imFile->imFile.getId().
//																	toString()).
//																	collect(Collectors.toSet());
//			 return imgs;
//		}
	
}
	

		
	

