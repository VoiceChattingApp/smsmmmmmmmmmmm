package com.chat.app.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.chat.app.entity.User;
import com.chat.app.entity.UserDto;

public interface UserService {
	User save(UserDto user);
	List <User> findAll();
	User findOne(String username);
	void sendVerificationEmail(User user, String siteUrl) throws UnsupportedEncodingException, MessagingException;
	boolean verify(String verificationCode);
}
