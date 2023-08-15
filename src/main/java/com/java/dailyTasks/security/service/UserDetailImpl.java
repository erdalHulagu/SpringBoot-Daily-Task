package com.java.dailyTasks.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.java.dailyTasks.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class UserDetailImpl implements UserDetails {


	private static final long serialVersionUID = 1L;
	
	
	private String email ;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
//    private List<GrantedAuthority> authorities;

//    public UserDetailImpl(User user) {
//        email=user.getEmail();
//        password=user.getPassword();
//        authorities= Arrays.stream(user.getRoles().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//    public UserDetailImpl(User user) {
//        email = user.getEmail();
//        password = user.getPassword();
//        authorities = user.getRoles().stream()
//            .map(role -> new SimpleGrantedAuthority(role.getType().name()))
//            .collect(Collectors.toList());
//    }

    public static UserDetailImpl build(User user) {
	     List<SimpleGrantedAuthority> authorities =   user.getRoles()
	    		 										   .stream()
	    		 										   . map(role->new SimpleGrantedAuthority(role.getType().name()))
	    		 										   .collect(Collectors.toList());											
	    		 																																					
	     return new UserDetailImpl(user.getEmail(), user.getPassword(), authorities);
}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
