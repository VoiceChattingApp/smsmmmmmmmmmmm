package com.chat.app.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private User user;
//	private UserDetails userDetails;

	public JwtResponse(String jwttoken, User user) {
		this.jwttoken = jwttoken;
//		this.userDetails = userDetails;
		this.user = user;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	public User getUser() {
		return this.user;
	}

//	public UserDetails getUserDetails() {
//		return userDetails;
//	}
	
}