package io.spring.flatfile.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.spring.flatfile.domain.Customer;
import io.spring.flatfile.domain.CustomerFieldsetMapper;

@Configuration
public class JobConfiguration {
	
	private int count = 0;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<Customer> flatFileReader() {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/data/customer.csv"));
		
		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<Customer>();
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id", "firstName", "lastName", "birthDate"});
		
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new CustomerFieldsetMapper());
		lineMapper.afterPropertiesSet();
		
		reader.setLineMapper(lineMapper);
		return reader;
	}
	
	@Bean
	public ItemWriter<Customer> itemWriter() {
		count++;
		System.out.println("Inside writer No - "+ count);
		
		return items -> {
			for (Customer cust : items) {
				System.out.println(cust.toString());
			}
		};
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
								.<Customer, Customer>chunk(10)
								.reader(flatFileReader())
								.writer(itemWriter())
								.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("flatfilejob3")
								.start(step1())
								.build();
	}

}
