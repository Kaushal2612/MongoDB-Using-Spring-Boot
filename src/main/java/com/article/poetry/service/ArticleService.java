package com.article.poetry.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.article.poetry.exception.ArticleException;
import com.article.poetry.model.User;

/**
 * This class is used to declare the service method
 * @author Kaushal Jhawar
 *
 */
public interface ArticleService {

	public void createUser(User user) throws ConstraintViolationException, ArticleException;
	
	public List<User> getAllUser();
	
	public User getOneUser(String id) throws ArticleException;
	
	public User updateUser(String id, User user) throws ArticleException;
	
	public void deleteOneUser(String id) throws ArticleException;
	
}
