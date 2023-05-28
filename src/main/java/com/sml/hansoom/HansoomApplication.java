package com.sml.hansoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.sml.hansoom.security.JwtAuthenticationFilter;

@SpringBootApplication
public class HansoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HansoomApplication.class, args);
	}

}
