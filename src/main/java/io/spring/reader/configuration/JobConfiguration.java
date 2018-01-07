package io.spring.reader.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spring.reader.StatelessItemReader;

@Configuration
public class JobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean 
	public StatelessItemReader stateLessItemReader() {
		List<String> data = new ArrayList<String>();
		data.add("One");
		data.add("Two");
		data.add("Three");
		return new StatelessItemReader(data);
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
								 .<String, String>chunk(2)
								 .reader(stateLessItemReader())
								 .writer(list -> {
									 for (String currItem : list) {
										 System.out.println(currItem + list.size());
									 }
								 }).build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("jobReader2")
								.start(step1())
								.build();
	}
}
