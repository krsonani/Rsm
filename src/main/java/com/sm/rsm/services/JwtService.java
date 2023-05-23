package com.sm.rsm.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public String genrateToken(String username)
	{
		Map<String,Object> claims = new HashMap<>();
		return createToken(claims,username);
	}
	
	public String createToken(Map<String,Object> claims,String username)
	{
		
		return Jwts.builder().setClaims(claims ).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000*60*30)).signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}
	//"ThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8"
	private Key getSignKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode("743677397A24432646294A404E635266556A586E5A7234753778214125442A47");
		return Keys.hmacShaKeyFor(keyBytes);
 	}
	
	public String extractUsername(String token)
	{
		return extractClaim(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token)
	{
		return extractClaim(token,Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token,Function<Claims,T> claimsResolver)
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public Boolean isTokenExpired(String token)
	{
		System.out.println(token);
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean ValidateToken(String token,UserDetails userDetails)
	{
		final String  username=extractUsername(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
}
