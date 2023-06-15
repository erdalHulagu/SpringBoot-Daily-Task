package com.java.dailyTasks.reguestBody;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class UserRequest {


@NotNull
@NotBlank(message="Please provide your First name")
private String firstName;

@NotNull
@NotBlank(message="Please provide your Last name")
private String lastName;

@NotNull
private String state;

@NotNull
private String city;

@Email(message = "Please provide valid email")
@Size(min = 10, max = 80)
private String email;

//@Column
//private String image;

//@Pattern(regexp = "\\\\d{3}-\\\\d{3}-\\\\d{4}",	// 999-999-9999
//message = "Please provide valid phone number" ) 
//@Column(nullable = false)
//private String phone;


//@NotBlank(message="Please provide time")
private LocalDateTime createAt;





}
