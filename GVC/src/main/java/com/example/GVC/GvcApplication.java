package com.example.GVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GvcApplication.class, args);
	}

}
