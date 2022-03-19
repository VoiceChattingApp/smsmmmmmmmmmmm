package com.chat.app.service;

import com.chat.app.entity.Role;

public interface RoleService {
	Role findByName(String name);
	Role save(Role role);
}
