package com.example.omf_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OmfSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmfSearchApplication.class, args);
	}

}
