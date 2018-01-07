package io.spring.decision.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecisionBatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step startStep() {
			return stepBuilderFactory.get("startStep")
					.tasklet((contribution, chunkContext) -> {
						System.out.println("Executing startStep");
						return RepeatStatus.FINISHED;
					})
					.build();
	}
	
	@Bean
	public Step evenStep() {
			return stepBuilderFactory.get("evenStep")
					.tasklet((contribution, chunkContext) -> {
						System.out.println("Executing evenStep");
						return RepeatStatus.FINISHED;
					})
					.build();
	}
	
	@Bean
	public Step oddStep() {
			return stepBuilderFactory.get("oddStep")
					.tasklet((contribution, chunkContext) -> {
						System.out.println("Executing oddStep");
						return RepeatStatus.FINISHED;
					})
					.build();
	}
	
	@Bean
	public JobExecutionDecider decider() {
		return new OddDecider();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("decisionJob9")
				.start(startStep())
				.next(decider())
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN1").to(evenStep())
				.from(oddStep()).on("*").to(decider())
				.end()
				.build();
	}
	
	

	public static class OddDecider implements JobExecutionDecider {

		int count = 0;

		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution){
			count++;
			if (count % 2 == 0) {
				return new FlowExecutionStatus("EVEN1");
			}
			else {
				return new FlowExecutionStatus("ODD");
			}
		}

	}

}
