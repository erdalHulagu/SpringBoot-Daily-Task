package com.java.dailyTasks.DTO;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

//private Long id;

@NotNull
@NotBlank(message = "Type first name please")
private String firstName;

@NotNull
@NotBlank(message = "Type last name please")
private String lastName;

@NotNull
@NotBlank(message = "Type state please")
private String state;

@NotNull
@NotBlank(message = "Type city please")
private String city;

@Email(message = "Please provide valid email")
@Size(min = 10, max = 80)
@NotBlank(message = "Type email please")
private String email;


//@Pattern(regexp = "\\\\d{3}-\\\\d{3}-\\\\d{4}",	// 999-999-9999
//message = "Please provide valid phone number" ) 
//@Column(nullable = false)
//private String phone;


private LocalDateTime createAt;


private Set<String> image;



	
}



