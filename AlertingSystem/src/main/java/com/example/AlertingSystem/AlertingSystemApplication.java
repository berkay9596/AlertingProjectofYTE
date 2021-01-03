package com.example.AlertingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

@EnableAsync
@EnableScheduling
public class AlertingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertingSystemApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();

	}

}
