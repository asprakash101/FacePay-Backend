package com.example.facepay.FacePay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(".Repository.ImageRepository")
public class FacePayBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacePayBackendApplication.class, args);
	}

}
