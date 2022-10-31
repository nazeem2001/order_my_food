package com.example.orderservice.security;

import java.util.Date;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.example.orderservice.config.JwtConfig;
import com.example.orderservice.exception.JwtTokenMalformedException;
import com.example.orderservice.exception.JwtTokenMissingException;
import com.example.orderservice.models.JWTData;



@Component
public class JwtTokenUtil {

	private final JwtConfig config;
	private final static String USER_NAME_KEY="userName";
	private final static String USER_ID_KEY="userId";

	public JwtTokenUtil(JwtConfig config) {
		this.config = config;
	}

	

	public JWTData  validateToken(final String header) throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			
			String jsonString =Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(header).getBody().getSubject();
			JSONObject jsonObject =new JSONObject(jsonString);
			return(new JWTData(jsonObject.getString(USER_NAME_KEY),jsonObject.getString(USER_ID_KEY)));
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}
}
