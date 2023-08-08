package com.java.dailyTasks.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {
	
	public static  final String SECRETKEY ="cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";
	 
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${furkanVakfiChat.app.SECRETKEY}")  // burada istersek yukarida kullanacagimiz jwt secret key yerine application.yml da se ettik
	private String SecretKey;                   // @Value annotaionu verip yml da set ettigimiz secreti yazdik parantez icinde, ve Expiration ms olarakda aynisini yaptik
	 
	@Value("${furkanVakfiChat.app.JwtExpirationMs}")
	private String JwtExpiration;
	

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
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

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
        byte[] keyBytes= Decoders.BASE64.decode(SECRETKEY);     /// bu ve ustteki createToken methodunun yaptigi isi Altta direk User detailsin hazir methodunu kullanarak yaprik
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(UserDetails userDetails) {
 		return Jwts.builder().setSubject(userDetails.getUsername())
 				             .setIssuedAt(new Date(System.currentTimeMillis()))
 				             .setExpiration(new Date(System.currentTimeMillis()+1000*60*30)).
 				              signWith(getSignKey(),SignatureAlgorithm.HS512).
 				              compact();
}
}