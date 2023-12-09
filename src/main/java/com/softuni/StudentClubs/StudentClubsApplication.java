package com.softuni.StudentClubs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.softuni.StudentClubs")
public class StudentClubsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentClubsApplication.class, args);
	}

}
