package io.spring.flatfile;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class FlatFileApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FlatFileApplication.class, args);
	}

}