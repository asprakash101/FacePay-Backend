package com.example.facepay.FacePay;

import com.example.facepay.FacePay.Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class FacePayBackendApplication {

	@Bean
	MongoMappingContext springDataMongoMappingContext() {
		return new MongoMappingContext();
	}

	public static void main(String[] args) {
		SpringApplication.run(FacePayBackendApplication.class, args);
	}

}
