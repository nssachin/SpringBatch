package io.springs.listeners.configuration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println(String.format("%s starting the job", jobExecution.getJobInstance().getJobName()));
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println(String.format("%s completed the job", jobExecution.getJobInstance().getJobName()));
		
	}

}
