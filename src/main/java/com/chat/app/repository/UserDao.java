package com.chat.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.app.entity.User;

public interface UserDao extends MongoRepository<User, String> {
	User findByUsername(String userame);

	User findByVerificationCode(String verificationCode);
}
