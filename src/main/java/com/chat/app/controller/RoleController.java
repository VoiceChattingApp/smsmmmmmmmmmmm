package com.chat.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.app.entity.Role;
import com.chat.app.repository.RoleDao;

@RestController
public class RoleController {
	@Autowired 
	private RoleDao roleDao;
	
	@PreAuthorize("permitAll()")
	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		 return roleDao.save(role);
	}
}
