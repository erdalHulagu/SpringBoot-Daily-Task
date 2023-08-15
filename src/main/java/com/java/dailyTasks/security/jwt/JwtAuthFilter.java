package com.java.dailyTasks.security.jwt;

import java.io.IOException;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.java.dailyTasks.security.service.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



// Burada elimizde bulunan tokeni extract edecegiz yani AuthenticationMAnagerin tanimasini saglayacagiz
@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	

	@Autowired
	private UserDetailsService userDetailsService;

//	 @Override
//	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//	        String authHeader = request.getHeader("Authorization");
//	        String token = null;
//	        String username = null;
//	        if (StringUtils.hasText(authHeader) && authHeader != null && authHeader.startsWith("Bearer ")) {
//	            token = authHeader.substring(7);
//	            username = jwtService.extractUsername(token);
//	        }
//
//	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//	            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
//	            if (jwtService.validateToken(token, userDetails)) {
//	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                SecurityContextHolder.getContext().setAuthentication(authToken);
//	            }
//	        }
//	        filterChain.doFilter(request, response);
//	    }
//	// filtrelenmemesini istediğim end-pointler
//	 @Override
//		protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//			AntPathMatcher antPathMatcher = new AntPathMatcher();
//			return antPathMatcher.match("/register", request.getServletPath()) || 
//					      antPathMatcher.match("/login",request.getServletPath());
//		}
		
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = parseJwt(request);
		
		try {
			if(jwtToken!=null && jwtUtils.validateToken(jwtToken)) {
				String email = jwtUtils.getEmailFromToken(jwtToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				
				// Valide edilen User bilgilerini SecurityContext e gönderiyoruz
				UsernamePasswordAuthenticationToken authenticationToken = new 
						UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
		} catch (Exception e) {
			logger.error("User not Found{} :" , e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
	
	// filtrelenmemesini istediğim end-pointler
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match("/register", request.getServletPath()) || 
				      antPathMatcher.match("/login",request.getServletPath());
	}

}
	


