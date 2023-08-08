package com.java.dailyTasks.request;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	@Size(max=50)
	@NotBlank(message="Please provide yout First Name")
	private String firstName;
	
	@Size(max=50)
	@NotBlank(message="Please provide yout Last Name")
	private String lastName;
	
	@Size(min=5, max=80)
	@Email(message="Please provide valid e-mail")
	private String email;
	
	@Size(min=4, max=20, message="Please provide Correct Size of Password")
	@NotBlank(message="Please provide your password")
	private String password;
	
	 @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
	            message = "Please provide valid phone number")
	@Size(min=14, max=14)
    @NotBlank(message = "Please provide your phone number")
	private String phoneNumber;
	
    @Size(max= 100)
    @NotBlank(message = "Please provide your address")
	private String address;

	
    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updateAt;

    private Set<String> image;

	public String getImageId() {
		// TODO Auto-generated method stub
		return null;
	}
}