package com.redbird.shopsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@OpenAPIDefinition
@EnableEurekaClient
public class ShopsServiceApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ShopsServiceApplication.class, args);
	}

}
