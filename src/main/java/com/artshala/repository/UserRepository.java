package com.artshala.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.artshala.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{'uniqueId': ?0 }")
	Optional<User> findByUniqueId(String id);

	@Query("{'id': ?0 }")
	Optional<User> findById(String id);

	@Query(value = "{'uniqueId': ?0 }", delete = true)
	Optional<User> deleteByUniqueId(String id);

	@Query("{'name': ?0 }")
	Optional<User> findByName(String id);

	@Query("{'email': ?0 }")
	Optional<User> findByEmail(String id);

}