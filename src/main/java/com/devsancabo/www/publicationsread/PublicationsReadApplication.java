package com.devsancabo.www.publicationsread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class PublicationsReadApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicationsReadApplication.class, args);
	}

}
