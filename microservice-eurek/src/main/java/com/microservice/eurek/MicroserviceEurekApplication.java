package com.microservice.eurek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroserviceEurekApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceEurekApplication.class, args);
	}

}
