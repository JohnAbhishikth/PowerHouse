//package com.lti.solvathon.jwt.config;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.lti.solvathon.dto.LoginDTO;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtTokenUtil implements Serializable {
//
//	private static final long serialVersionUID = -2550185165626007488L;
//
//	public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
//
//	@Value("${jwt.secret}")
//	private String secret;
//
//	public String generateToken(LoginDTO loginDTO) {
//		Map<String, Object> claims = new HashMap<>();
//		return doGenerateToken(claims, loginDTO.getId());
//	}
//
//	private String doGenerateToken(Map<String, Object> claims, String subject) {
//		return Jwts.builder().setClaims(claims)
//				.setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
//				.signWith(SignatureAlgorithm.HS512, secret).compact();
//	}
//
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//	
//	public Date getIssuedAtDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getIssuedAt);
//	}
//	
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//	 public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		 final Claims claims = getAllClaimsFromToken(token);
//		 return claimsResolver.apply(claims);
//	 } 
//	 
//	 private Claims getAllClaimsFromToken(String token) {
//		 return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//	 }
//	 
//	 private Boolean isTokenExpired(String token) {
//		 final Date expiration = getExpirationDateFromToken(token);
//		 return expiration.before(new Date());
//	 }
//	 
//	 private Boolean ignoreTokenExpiration(String token) {
//		 return false;
//	 }
//	 
//	 public Boolean canTokenBeRefreshed(String token) {
//		 return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//	 }
//	 
//	 public Boolean ValidateToken(String token, String id) {
//	 final String username = getUsernameFromToken(token);
//	 return (username.equals(id) && !isTokenExpired(token));
//	 }
//	 
//}
