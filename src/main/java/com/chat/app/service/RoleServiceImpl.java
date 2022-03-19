package com.chat.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.app.entity.Role;
import com.chat.app.repository.RoleDao;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired 
	private RoleDao roleDao;

	@Override
	public Role findByName(String name) {
		return roleDao.findByName(name);
	}

	@Override
	public Role save(Role role) {
		return roleDao.save(role);
	}

}
