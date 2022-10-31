package com.example.gateway.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gateway.models.ErrorResponseDto;
import com.example.gateway.entity.User;
import com.example.gateway.models.JwtRequest;
import com.example.gateway.models.JwtResponse;
import com.example.gateway.security.JwtTokenUtil;
import com.example.gateway.repository.UserRepository;


@RestController
public class JwtAuthenticationController {

	@Autowired
	private final JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	/**
	 * * *** NOTE: ***
	 * * Api Gateway should match predicate
	 * * path to be discoverable in swagger
	 */
	@RequestMapping(value ="gateway/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		Optional<User> status = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		System.out.println(status);
		if (status.isEmpty()) {
			List<String> details = new ArrayList<>();
			details.add("Invalid Username or password");
			ErrorResponseDto error = new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, "uri");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
		JSONObject user =new JSONObject();
		user.put("userId", status.get().getUserId());
		user.put("userName", status.get().getUsername());
		final String token = jwtTokenUtil.generateToken(user.toString());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private Optional<User> authenticate(String username, String password) {
		Optional<User> status=Optional.empty();
		System.out.println(password+" "+username);

		Optional<User> user=userRepository.findByUsername(username);
		if (user.isPresent()) {
			System.out.println("passecv"+user.get().getPassword());
			if(encoder.matches(password, user.get().getPassword())){
				System.out.println("okKKKKKKKK");
				return user;
			}
		}

		return status;
	}
	
}
