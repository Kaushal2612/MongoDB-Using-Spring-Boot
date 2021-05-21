package com.article.poetry.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.article.poetry.exception.ArticleException;
import com.article.poetry.model.User;
import com.article.poetry.repository.ArticleRepository;
import com.article.poetry.service.ArticleService;

/**
 * This class is to handle the User CRUD operation
 * @author Kaushal Jhawar
 *
 */
@RestController
public class ArticleController {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;
	
	/**
	 * Retrieve All User
	 * @return
	 */
	@GetMapping("/readUser")
	public ResponseEntity<?> readAllUser(){
		
		List<User> user = articleService.getAllUser();
		return new ResponseEntity<>(user, user.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND );
	}

	/**
	 * Create new User validate if mandatory field are present or not.
	 * else throw an exception
	 * @param user
	 * @return
	 */
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody User user){
		try {
			articleService.createUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (ArticleException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Read User According to Id
	 * @param id
	 * @return
	 */
	@GetMapping("readUser/{id}")
	public ResponseEntity<?> readOneUser(@PathVariable String id){

		try {
			return new ResponseEntity<User>(articleService.getOneUser(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("User not found with id "+id, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("updateUser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User userBodyReceived){

		try {
			articleService.updateUser(id, userBodyReceived);
			return new ResponseEntity<>("Updated User with id " + id, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (ArticleException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete user by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
		
		try{
			articleService.deleteOneUser(id);
			return new ResponseEntity<>("Successfully deleted user with id " + id, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
