package com.chat.app.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chat.app.entity.Photo;
import com.chat.app.service.PhotoService;

@CrossOrigin
@PreAuthorize("permitAll()")
@RestController
public class PhotoController {
	@Autowired
	private PhotoService photoService;

	@PostMapping("/photos/{id}")
	public String addPhoto(@PathVariable String id, @RequestParam("image") MultipartFile image) throws IOException {
		return photoService.addPhoto(id, image);
	}

	@GetMapping("/photos/{id}")
	public String getPhoto(@PathVariable String id) {
		Photo photo = photoService.getPhoto(id);
		return Base64.getEncoder().encodeToString(photo.getImage().getData());
	}
}