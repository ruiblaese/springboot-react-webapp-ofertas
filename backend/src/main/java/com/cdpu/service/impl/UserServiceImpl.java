package com.cdpu.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdpu.entity.User;
import com.cdpu.repository.UserRepository;
import com.cdpu.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repository;

	@Override
	public User save(User u) {

		return repository.save(u);
	}

	@Override
	public Optional<User> findByEmail(String email) {

		return repository.findByEmailEquals(email);
	}

}
