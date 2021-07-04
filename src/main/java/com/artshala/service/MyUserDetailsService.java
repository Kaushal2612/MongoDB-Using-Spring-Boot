package com.artshala.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.artshala.repository.UserRepository;

/**
 * Get the email as username from the database and return the email and password
 * @author Kaushal Jhawar
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<com.artshala.model.User> userOptional = userRepository.findByEmail(email);
		return new User(userOptional.get().getEmail(), userOptional.get().getPassword(), new ArrayList<>());
	}

}
