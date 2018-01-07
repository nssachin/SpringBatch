package io.spring.reader;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ItemReaderApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ItemReaderApplication.class, args);
	}
}
