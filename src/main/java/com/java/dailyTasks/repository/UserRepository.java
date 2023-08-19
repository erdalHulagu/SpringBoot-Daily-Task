package com.java.dailyTasks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.dailyTasks.domain.Image;
import com.java.dailyTasks.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = "roles")
	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = "image")
	Optional<User>  findById(Long id);

	@EntityGraph(attributePaths = { "image"})
	List<User> findAll();

	Boolean existsByEmail(String email);

	@Query( "SELECT count(*) from User u join u.image img where img.id=:id")
	Integer findUserCountByImageId(String id);

	
	
	

//	@Query( "SELECT count(*) from User u join u.image img where img.id=:id")
//	Integer findCountingById(@Param("id") Image imageFile );



	

}
