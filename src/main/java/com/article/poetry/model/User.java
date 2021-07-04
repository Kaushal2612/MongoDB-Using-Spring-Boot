package com.article.poetry.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.annotations.ApiModelProperty;
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
	private String uniqueId;

	@Email(regexp = ".*@.*\\..*", message = "Email should be valid")
	@ApiModelProperty(notes = "Email of the User", name = "email", required = true)
	@NotEmpty(message = "Email cannot be empty")
	@NotNull(message = "Email cannot be null")
	private String email;

	@ApiModelProperty(notes = "Name of the User", name = "name", required = true)
	@NotEmpty(message = "Name cannot be empty")
	@NotNull(message = "Name cannot be null")
	private String name;

	@ApiModelProperty(notes = "Password of the User", name = "password", required = true)
	@NotEmpty(message = "Password cannot be empty")
	@NotNull(message = "Password cannot be null")
	private String password;
	
	private String description;

	@ApiModelProperty(notes = "Date of birth of User", name = "Date of Birth")
	private Date dob;

}
