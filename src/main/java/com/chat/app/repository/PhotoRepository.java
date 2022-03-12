package com.chat.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.app.entity.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {
	
}
