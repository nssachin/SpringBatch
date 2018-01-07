package io.spring.database.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spring.database.domain.Customer;
import io.spring.database.domain.CustomerRowMapper;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	/*@Bean
	public JdbcCursorItemReader<Customer> customerItemReader() {
		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<Customer>();
		reader.setDataSource(dataSource);
		reader.setRowMapper(new CustomerRowMapper());
		reader.setSql("Select id, firstName, lastName, birthDate from Customer order by firstName, lastName");

		return reader;
	}*/
	
	@Bean
	public JdbcPagingItemReader<Customer> customerItemReader() {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<Customer>();
		reader.setDataSource(dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomerRowMapper());
		
		PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthDate");
		queryProvider.setFromClause("from Customer");
		
		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.DESCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);
		
		return reader;
		
	}
 
	@Bean
	public ItemWriter<Customer> customerItemWriter() {
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
								 .reader(customerItemReader())
								 .writer(customerItemWriter())
								 .build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("databaseJob2")
								.start(step1())
								.build();
	}
}
