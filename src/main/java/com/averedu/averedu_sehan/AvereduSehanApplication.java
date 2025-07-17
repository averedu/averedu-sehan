package com.averedu.averedu_sehan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AvereduSehanApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AvereduSehanApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AvereduSehanApplication.class);
	}

}
