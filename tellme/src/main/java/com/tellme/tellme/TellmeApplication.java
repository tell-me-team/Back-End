package com.tellme.tellme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TellmeApplication {

	public static void main(String[] args) {

//		new SpringApplicationBuilder(TellmeApplication.class)
//				.properties("spring.config.location = classpath:/application.yml, classpath:/env.properties")
//						.run(args);
		SpringApplication.run(TellmeApplication.class, args);
	}

}
