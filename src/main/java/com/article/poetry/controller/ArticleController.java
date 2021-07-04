package com.article.poetry.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.article.poetry.exception.ArticleException;
import com.article.poetry.model.AuthenticationRequest;
import com.article.poetry.model.AuthenticationResponse;
import com.article.poetry.model.User;
import com.article.poetry.repository.ArticleRepository;
import com.article.poetry.service.ArticleService;
import com.article.poetry.service.JwtUtil;
import com.article.poetry.service.MyUserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * This class is to handle the User CRUD operation
 * @author Kaushal Jhawar
 *
 */
@Api(value = "Operation pertaining to user")
@RestController
public class ArticleController {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	/**
	 * Retrieve All User
	 * @return
	 */
	@ApiOperation(value = "Get the list of User", tags = "getAllUser()", authorizations = { @Authorization(value="Bearer") })
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
	@ApiOperation(value = "Sign Up", tags = "createUser()")
	@PostMapping("/signUp")
	public ResponseEntity<?> createUser(@RequestBody User user){
		try {
			articleService.createUser(user);
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(user.getEmail());

			final String jwt = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));

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
	@ApiOperation(value = "get one user information", response=User.class, tags = "getOneUser()")
	@GetMapping("readUser/{id}")
	public ResponseEntity<?> readOneUser(@PathVariable String id){

		try {
			return new ResponseEntity<User>(articleService.getOneUser(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("User not found with id "+id, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Delete the user by user id
	 * @param id
	 * @param userBodyReceived
	 * @return
	 */
	@ApiOperation(value = "update the user information", tags = "updateUser()")
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
	@ApiOperation(value = "delete the user information", tags = "deleteOneUser()")
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
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
