package com.example.teamOnebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class teamOnebeApplication {

	public static void main(String[] args) {
		SpringApplication.run(teamOnebeApplication.class, args);
	}

}
