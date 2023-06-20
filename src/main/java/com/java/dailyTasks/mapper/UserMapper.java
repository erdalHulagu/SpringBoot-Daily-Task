package com.java.dailyTasks.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.java.dailyTasks.DTO.UserDTO;
import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.User;
import com.java.dailyTasks.request.UserRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "image" , source = "image", qualifiedByName =  "getImageAsLong" )
	User userDTOToUser(UserDTO userDTO);
  
	
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "image" , source = "image", qualifiedByName =  "getImageAsLong" )
	User userRequestToUser(UserRequest userRequest);
	

    @Mapping(target = "id", ignore = true )
	@Mapping(target = "image", source = "image", qualifiedByName = "imageLong")
	UserDTO userToUserDto(User user);
	
	
	List<UserDTO> userToUserDTOList(List<User> userList);
	  
	 // long turunde image i image turunde image e cevidik
	 @Named("getImageAsLong")
    static Set<Image> map(Set<Long> imageUrls) {
        Set<Image> images = new HashSet<>();
        for (Long imageUrl : imageUrls) {
            Image image = new Image();
            image.setId(imageUrl);
            images.add(image);
        }
        return images;
	 }


	  
     // image turunde imageyi long turunde imageye cevirdik
	  @Named("imageLong")
	    static Set<Long> maper(Set<Image> imageSet) {
	        return imageSet.stream()
	                .map(Image->Image.getId())
	                .collect(Collectors.toSet());
	    }
	
	
	
}
	

		
	

