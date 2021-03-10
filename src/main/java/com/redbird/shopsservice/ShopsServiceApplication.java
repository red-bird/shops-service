package com.redbird.shopsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@OpenAPIDefinition
@EnableEurekaClient
public class ShopsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopsServiceApplication.class, args);
	}

}
