package com.artshala.exception;

/**
 * This class handles artshala exception
 * @author Kaushal Jhawar
 *
 */
public class ArtshalaException extends Exception {

	/**
	 *  https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
	 */
	private static final long serialVersionUID = 1L;

	public ArtshalaException(String message) {
		super(message);
	}

	public static String UserNotFoundException(String id) {
		return "User not found with id " + id;
	}
	
}
