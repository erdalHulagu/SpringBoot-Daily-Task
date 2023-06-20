package com.java.dailyTasks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dailyTasks.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = "image")
	Optional<User>  findById(Long id);

	

}
