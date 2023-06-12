package com.gogonew.api.job;

import static com.gogonew.api.common.BatchComponent.*;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.gogonew.api.mysql.domain.pocket.Pocket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PocketSendMailJobConfig {
	private static final String PREFIX = "PocketSendMail";

	private static final int CHUNK_SIZE = 100;
	private static final int RETRY_LIMIT = 3;

	@Bean(name = PREFIX + EXECUTOR)
	public TaskExecutor mailTaskExecutor() {
		return new SyncTaskExecutor();
	}

	@Bean(name = PREFIX + JOB)
	public Job mailJob(
		@Qualifier(PREFIX + STEP) Step step) {
		return new JobBuilder(PREFIX + JOB)
			.start(step)
			.build();
	}

	@Bean(name = PREFIX + STEP)
	@JobScope
	public Step mailStep(
		@Qualifier(PREFIX + EXECUTOR) TaskExecutor taskExecutor,
		@Qualifier(PREFIX + READER) ItemReader reader,
		@Qualifier(PREFIX + PROCESSOR) ItemProcessor processor,
		@Qualifier(PREFIX + WRITER) ItemWriter writer) {
		return new StepBuilder(PREFIX + STEP)
			.<Pocket, Pocket>chunk(CHUNK_SIZE)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.faultTolerant()
			.retryLimit(RETRY_LIMIT)
			.retry(Exception.class)
			.taskExecutor(taskExecutor)
			.build();
	}

	@Bean(name = PREFIX + READER)
	@StepScope
	public JpaPagingItemReader<Pocket> pocketMailReader() {
		return new JpaPagingItemReader<>();
	}

	@Bean(name = PREFIX + PROCESSOR)
	@StepScope
	public ItemProcessor<Pocket, Pocket> pocketMailProcessor() {
		return pocket -> pocket;
	}

	@Bean(name = PREFIX + WRITER)
	@StepScope
	public JpaItemWriter<Pocket> pocketMailWriter() {
		return new JpaItemWriter<>();
	}
}
