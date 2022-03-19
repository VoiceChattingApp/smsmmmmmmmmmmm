package com.chat.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.config.JwtTokenUtil;
import com.chat.app.entity.JwtRequest;
import com.chat.app.entity.JwtResponse;
import com.chat.app.entity.User;
import com.chat.app.entity.UserDto;
import com.chat.app.service.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userDetailsService;
	
	@PreAuthorize("permitAll()")
	@CrossOrigin
	@GetMapping
	public String home() {
		return "Hello World";
	}
	
	@PreAuthorize("permitAll()")
	@CrossOrigin
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		String email = authenticationRequest.getUsername();
		com.chat.app.entity.User user = userDetailsService.findOne(email);
		
		if(user.getEnabled() == 0) {
			throw new Exception("Account not verified");
		}
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(), 
						authenticationRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtTokenUtil.generateToken(authentication);

		return ResponseEntity.ok(new JwtResponse(token, user));
	}
	
	@PreAuthorize("permitAll()")
	@CrossOrigin
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDto user, HttpServletRequest request) throws Exception {
		com.chat.app.entity.User savedUser = userDetailsService.save(user);
		String siteUrl = request.getRequestURL().toString();
		siteUrl.replace(request.getServletPath(), "");
		userDetailsService.sendVerificationEmail(savedUser, siteUrl);
		return ResponseEntity.ok(savedUser);
	}
	
	
	
	@GetMapping("/register/verify")
	public ResponseEntity <?> verifyAccount(@Param("code") String code) {
		boolean verified = userDetailsService.verify(code);
		return verified ? ResponseEntity.ok("Congratulations! Your account has been verified.\nYou can now login using your email and password")
						: ResponseEntity.ok("Your account was already verified or the verification code is invalid");
	}
	
	@CrossOrigin
	@GetMapping("/users")
	public List <User> getUsers() {
		return userDetailsService.findAll();
	}
	
	@PreAuthorize("permitAll()")
	@CrossOrigin
	@GetMapping("/user/{email}")
	public ResponseEntity <?> findByUsername(@PathVariable String email) {
		com.chat.app.entity.User user = userDetailsService.findOne(email);
		return ResponseEntity.ok(user);
	}
}
