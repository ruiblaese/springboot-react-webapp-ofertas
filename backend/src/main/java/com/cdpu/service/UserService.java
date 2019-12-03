package com.cdpu.service;

import java.util.Optional;

import com.cdpu.entity.User;

public interface UserService {
	
	User save(User u);
	
	Optional<User> findByEmail(String email);
}
