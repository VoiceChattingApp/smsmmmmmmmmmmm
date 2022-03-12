package com.chat.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chat.app.entity.Role;

@Repository
public interface RoleDao extends MongoRepository<Role, String> {
	Role findByName(String name);
}
