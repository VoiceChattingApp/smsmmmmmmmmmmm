package com.chat.app.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.entity.Photo;
import com.chat.app.repository.PhotoRepository;

@Service
public class PhotoService {
	@Autowired
	private PhotoRepository photoRepository;
	
	public String addPhoto(String id, MultipartFile file) throws IOException {
		Photo photo = new Photo();
		photo.setId(id);
		photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		Photo temp = photoRepository.save(photo);
		return temp.getId();
	}
	
	public Photo getPhoto(String id) {
		return photoRepository.findById(id).get();
	}
}
