package io.springs.listeners;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ListenerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ListenerApplication.class, args);
	}
}
