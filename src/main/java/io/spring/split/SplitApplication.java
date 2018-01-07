package io.spring.split;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SplitApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SplitApplication.class, args);
	}

}