package com.artshala.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.artshala.exception.ArtshalaException;
import com.artshala.model.User;
import com.artshala.repository.UserRepository;
import com.artshala.util.ArtshalaUtil;

/**
 * This class implement the services for User operation
 * @author Kaushal Jhawar
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Create the user
	 */
	@Override
	public void createUser(User user) throws ArtshalaException {
		
		Optional<User> userOptional = userRepository.findById(user.getId());
		if (userOptional.isPresent()) {
			throw new ArtshalaException(ArtshalaException.UserNotFoundException(user.getName()));
		}
		else {
			user.setUniqueId(ArtshalaUtil.generateUniqueId());
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		}
		
	}

	@Override
	public List<User> getAllUser() {
		
		List<User> userList = userRepository.findAll();
		if(!userList.isEmpty()) {
			return userList;
		}
		return new ArrayList<User>();
	}

	@Override
	public User getOneUser(String id) throws ArtshalaException {

		Optional<User> userOptional = userRepository.findByUniqueId(id);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		else {
			throw new ArtshalaException(ArtshalaException.UserNotFoundException(id));
		}

	}

	/**
	 * This method is first searching by unique Id, if not found then by Mongodb Id and updating it
	 */
	@Override
	public User updateUser(String id, User userBodyReceived) throws ArtshalaException {

		Optional<User> userOptional = userRepository.findByUniqueId(id);
		
		if(!userOptional.isPresent()) {
			userOptional = userRepository.findById(id);
		}
		
		if(userOptional.isPresent()) {
	
			User userToUpdate = userOptional.get();
			
			// first check whether the body field received is null. If not, update it with the received, else keep it as it is.
			// update email
			userToUpdate.setEmail(userBodyReceived.getEmail()!=null ? userBodyReceived.getEmail() : userToUpdate.getEmail());
			// update Name
			userToUpdate.setName(userBodyReceived.getName()!=null ? userBodyReceived.getName() : userToUpdate.getName());
			// update Password
			userToUpdate.setPassword(userBodyReceived.getPassword()!=null ? bCryptPasswordEncoder.encode(userBodyReceived.getPassword()) : bCryptPasswordEncoder.encode(userToUpdate.getPassword()));
			// update Description
			userToUpdate.setDescription(userBodyReceived.getDescription()!=null ? userBodyReceived.getDescription() : userToUpdate.getDescription());
			// update dob
			userToUpdate.setDob(userBodyReceived.getDob()!=null ? userBodyReceived.getDob() : userToUpdate.getDob());
			
			userRepository.save(userToUpdate);
			return userToUpdate;
		
		} else {
			throw new ArtshalaException(ArtshalaException.UserNotFoundException(id));
		}
	}

	@Override
	public void deleteOneUser(String id) throws ArtshalaException {
		
		Optional userOptional = userRepository.deleteByUniqueId(id);
		if(!userOptional.isPresent()) {
			throw new ArtshalaException(ArtshalaException.UserNotFoundException(id));
		} else {
			userRepository.deleteById(id);
		}
		
	}

}
