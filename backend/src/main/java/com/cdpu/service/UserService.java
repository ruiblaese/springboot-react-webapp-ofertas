package com.cdpu.service;

import java.util.List;
import java.util.Optional;

import com.cdpu.entity.User;

public interface UserService {
	
	User save(User u);
	
	Optional<User> findByEmail(String email);
	
	List<User> findAll();
}
