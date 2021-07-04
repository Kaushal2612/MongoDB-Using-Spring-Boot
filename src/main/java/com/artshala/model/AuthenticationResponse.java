package com.artshala.model;

/**
 * This is the authentication response class for jwt token
 * @author nehaj
 *
 */
public class AuthenticationResponse {

	private final String jwt;
	
	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	
}
