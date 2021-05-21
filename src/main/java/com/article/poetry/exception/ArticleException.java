package com.article.poetry.exception;

public class ArticleException extends Exception {

	/**
	 *  https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
	 */
	private static final long serialVersionUID = 1L;

	public ArticleException(String message) {
		super(message);
	}

	public static String UserNotFoundException(String id) {
		return "User not found with id " + id;
	}
	
}
