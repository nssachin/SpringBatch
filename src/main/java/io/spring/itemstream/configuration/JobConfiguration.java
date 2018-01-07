package io.spring.itemstream.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.core.net.SyslogOutputStream;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public StatefulItemReader itemReader() {
		List<String> items = new ArrayList<String>();
		for (int i=0; i<=100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new StatefulItemReader(items);
	}
	
	@Bean
	public ItemWriter<String> itemWriter() {
		return items -> {
			for (String item : items) {
				System.out.println(item);
			}
		};
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
								 .<String, String>chunk(20)
								 .reader(itemReader())
								 .writer(itemWriter())
								 .build();
	}
	
	@Bean
	public Job statefulJob() {
		return jobBuilderFactory.get("statefullJob")
								.start(step1())
								.build();
	}
}
