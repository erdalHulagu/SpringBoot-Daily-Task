package com.java.dailyTasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.dailyTasks.domain.Activities;

@Repository
public interface ActivitiesRepositoy extends JpaRepository<Activities, Long>{

}
