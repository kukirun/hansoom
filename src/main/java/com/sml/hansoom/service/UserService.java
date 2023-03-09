package com.sml.hansoom.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sml.hansoom.model.UserEntity;
import com.sml.hansoom.persistence.UserRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("invalid arguments");
		}
		final String email  = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("email already exist {}",email);
			throw new RuntimeException("eamil already exists");
		}
	
		return userRepository.save(userEntity);
	}
	
	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByEmail(email);
		
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		
		return null;
	}
}
