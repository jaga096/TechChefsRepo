package com.tech.chefs.test.restproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tech.chefs")
public class RestProjetcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestProjetcApplication.class, args);
	}
}
