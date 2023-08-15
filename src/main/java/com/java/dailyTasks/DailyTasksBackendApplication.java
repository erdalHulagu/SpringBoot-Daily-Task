package com.java.dailyTasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.java.dailyTasks", "com.java.otherPackages"})
public class DailyTasksBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyTasksBackendApplication.class, args);
	}

}
