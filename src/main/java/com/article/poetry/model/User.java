package com.article.poetry.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the POJO class of User
 * 
 * @author Kaushal Jhawar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

	@Id
	private String id;

	@Field("id")
	// @Indexed(unique = true)
	private String uniqueId;

	@NotEmpty(message = "Email cannot be empty")
	@NotNull(message = "Email cannot be null")
	private String email;

	@NotEmpty(message = "Name cannot be empty")
	@NotNull(message = "Name cannot be null")
	private String name;

	private String description;

	private Date dob;

}
