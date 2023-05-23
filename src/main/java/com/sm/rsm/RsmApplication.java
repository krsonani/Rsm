package com.sm.rsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsmApplication.class, args);
	}

}
