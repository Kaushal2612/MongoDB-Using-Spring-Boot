package com.article.poetry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.article.poetry.model.User;

@Repository
public interface ArticleRepository extends MongoRepository<User, String> {

	@Query("{'uniqueId': ?0 }")
	Optional<User> findByUniqueId(String id);

	@Query("{'id': ?0 }")
	Optional<User> findById(String id);

	@Query("{'uniqueId': ?0 }")
	Optional<User> deleteByUniqueId(String id);

	@Query("{'name': ?0 }")
	Optional<User> findByName(String id);

}