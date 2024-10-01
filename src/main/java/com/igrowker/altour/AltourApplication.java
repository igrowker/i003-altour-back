package com.igrowker.altour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AltourApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltourApplication.class, args);
	}

}
