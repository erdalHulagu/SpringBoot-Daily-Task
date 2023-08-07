package com.java.dailyTasks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dailyTasks.domain.Role;
import com.java.dailyTasks.domain.enums.RoleType;


@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByType(RoleType type);

}
