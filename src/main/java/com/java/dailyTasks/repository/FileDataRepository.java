package com.java.dailyTasks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dailyTasks.domain.FileData;

public interface FileDataRepository extends JpaRepository<FileData, Long>{
	
	Optional<FileData> findById(Long id);
}
