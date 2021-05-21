package com.article.poetry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.article.poetry.exception.ArticleException;
import com.article.poetry.model.User;
import com.article.poetry.repository.ArticleRepository;
import com.poetry.article.util.PoetryUtil;

/**
 * This class implement the services for User operation
 * @author Kaushal Jhawar
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService{

	public ArticleServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * Create the user
	 */
	@Override
	public void createUser(User user) throws ArticleException {
		
		Optional<User> userOptional = articleRepository.findById(user.getId());
		if (userOptional.isPresent()) {
			throw new ArticleException(ArticleException.UserNotFoundException(user.getName()));
		}
		else {
			user.setUniqueId(PoetryUtil.generateUniqueId());
			articleRepository.save(user);
		}
		
	}

	@Override
	public List<User> getAllUser() {
		
		List<User> userList = articleRepository.findAll();
		if(userList.size()>0) {
			return userList;
		}
		return new ArrayList<User>();
	}

	@Override
	public User getOneUser(String id) throws ArticleException {

		Optional<User> userOptional = articleRepository.findByUniqueId(id);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		else {
			throw new ArticleException(ArticleException.UserNotFoundException(userOptional.get().getName()));
		}

	}

	@Override
	public User updateUser(String id, User userBodyReceived) throws ArticleException {

		// search by our unique Id
		Optional<User> userOptional = articleRepository.findByUniqueId(id);
		
		// search by mongodbId;
		if(!userOptional.isPresent()) {
			userOptional = articleRepository.findById(id);
		}
		
		if(userOptional.isPresent()) {
	
			User userToUpdate = userOptional.get();
			
			// first check whether the body field received is null. If not, update it with the received, else keep it as it is.
			// update email
			userToUpdate.setEmail(userBodyReceived.getEmail()!=null ? userBodyReceived.getEmail() : userToUpdate.getEmail());
			// update Name
			userToUpdate.setName(userBodyReceived.getName()!=null ? userBodyReceived.getName() : userToUpdate.getName());
			// update Description
			userToUpdate.setDescription(userBodyReceived.getDescription()!=null ? userBodyReceived.getDescription() : userToUpdate.getDescription());
			// update dob
			userToUpdate.setDob(userBodyReceived.getDob()!=null ? userBodyReceived.getDob() : userToUpdate.getDob());
			
			articleRepository.save(userToUpdate);
			return userToUpdate;
		
		} else {
			throw new ArticleException(ArticleException.UserNotFoundException(id));
		}
	}

	@Override
	public void deleteOneUser(String id) throws ArticleException {
		
		Optional userOptional = articleRepository.deleteByUniqueId(id);
		if(!userOptional.isPresent()) {
			throw new ArticleException(ArticleException.UserNotFoundException(id));
		} else {
			articleRepository.deleteById(id);
		}
		
	}

	
	
}