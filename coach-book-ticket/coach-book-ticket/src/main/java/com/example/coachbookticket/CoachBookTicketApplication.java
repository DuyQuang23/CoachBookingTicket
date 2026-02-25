package com.example.coachbookticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CoachBookTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoachBookTicketApplication.class, args);
	}

}
