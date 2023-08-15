package com.java.dailyTasks.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.java.dailyTasks.exception.message.ErrorMessage;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtils {
	
	 
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${furkanVakfiChat.app.SECRETKEY}")  // burada istersek yukarida kullanacagimiz jwt secret key yerine application.yml da se ettik
	private String secretKey;                   // @Value annotaionu verip yml da suslu parantez icinde set ettigimiz secreti yazdik 
	 
	

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//    

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |  
				SecurityException | IllegalArgumentException e ) {
			logger.error(String.format(ErrorMessage.JWTTOKEN_ERROR_MESSAGE, e.getMessage()));
		}
		return false ;
	}
	// JWT token içinden kullanıcının email bilgisi alınmacak
		public String getEmailFromToken(String token) {
			return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
			
		}
//
//    public String generateToken(String userName){
//        Map<String,Object> claims=new HashMap<>();
//        return createToken(claims,userName);
//    }
//
//    private String createToken(Map<String, Object> claims, String userName) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
//                .signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
//    }
 // JWT token üretecek
// 	
    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);     /// bu ve ustteki createToken methodunun yaptigi isi Altta direk User detailsin hazir methodunu kullanarak yaprik
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(UserDetails userDetails) {
 		return Jwts.builder().setSubject(userDetails.getUsername())
 				             .setIssuedAt(new Date())
 				             .setIssuedAt(new Date(System.currentTimeMillis()))
    	                     .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
			                 .signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
}
}