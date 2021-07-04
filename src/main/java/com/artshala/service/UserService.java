package com.artshala.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.artshala.exception.ArtshalaException;
import com.artshala.model.User;

/**
 * This class is used to declare the service method
 * @author Kaushal Jhawar
 *
 */
public interface UserService {

	public void createUser(User user) throws ConstraintViolationException, ArtshalaException;
	
	public List<User> getAllUser();
	
	public User getOneUser(String id) throws ArtshalaException;
	
	public User updateUser(String id, User user) throws ArtshalaException;
	
	public void deleteOneUser(String id) throws ArtshalaException;
	
}
