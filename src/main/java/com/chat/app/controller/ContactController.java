package com.chat.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.entity.Contact;
import com.chat.app.entity.User;
import com.chat.app.repository.UserDao;
import com.chat.app.service.UserService;

@RestController
@CrossOrigin
public class ContactController {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userDetailsService;

	@PreAuthorize("permitAll()")
	@GetMapping("/contacts/{username}")
	public ResponseEntity<?> getContacts(@PathVariable String username) {
		User user = userDetailsService.findOne(username);
		if (user != null) {
			return ResponseEntity.ok(user.getContacts());
		}
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("permitAll()")
	@PostMapping("/contacts/{username}")
	public List<Contact> postContact(@RequestBody Contact contact, @PathVariable String username) {
		User user = userDetailsService.findOne(username);
		if (user != null) {
			if (user.getContacts() == null) {
				List<Contact> contacts = new ArrayList<>();
				user.setContacts(contacts);
			}
			user.getContacts().add(contact);
			userDao.save(user);
			return user.getContacts();
		}
		return null;
	}
}
