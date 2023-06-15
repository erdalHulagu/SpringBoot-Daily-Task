package com.java.dailyTasks.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dailyTasks.domain.FileData;
import com.java.dailyTasks.domain.Image;

import java.util.Optional;


public interface ImageRepository extends JpaRepository<Image,Long> {
	


    Optional<FileData> findByName(String fileName);

	
	@EntityGraph(attributePaths = "id") 
	Optional<Image> findImageById(Long id);

	Optional<Image> findById(Long id);


	FileData save(FileData build);
	
}
