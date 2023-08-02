package com.tellme.tellme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TellmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TellmeApplication.class, args);
	}

}
