package com.artshala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * This class is used to validate the field data before persisting.
 * @author Kaushal Jhawar
 *
 */
@Configuration
public class ValidationConfig {

	/**
	 * This method is used to validate the data before persisting it to any database. 
	 * It gets trigger before we try to persist any data in the database.
	 * While persisting the data, this is gonna trigger if user sends any null value so 
	 * will trigger contraint violation exception
	 * @return
	 */
	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}
	
	/**
	 * Implementation class for the validation.
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}
