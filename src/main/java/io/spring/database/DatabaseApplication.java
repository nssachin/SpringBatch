package io.spring.database;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DatabaseApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DatabaseApplication.class, args);
	}

}
