package com.gogonew.api.job.email;

import static com.gogonew.api.common.BatchComponent.*;

import java.time.LocalDateTime;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.gogonew.api.email.EmailRepository;
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.pocket.Pocket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PocketSendMailJobConfig {
	public static final String PREFIX = "PocketSendMail";

	private static final int CHUNK_SIZE = 30;
	private static final int RETRY_LIMIT = 2;

	private final PlatformTransactionManager transactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final JobRepository jobRepository;
	private final EmailRepository emailRepository;

	@Bean(name = PREFIX + EXECUTOR)
	public TaskExecutor taskExecutor() {
		return new SyncTaskExecutor();
	}

	@Bean(name = PREFIX + JOB)
	public Job job(
		@Qualifier(PREFIX + STEP) Step step) {
		return new JobBuilder(PREFIX + JOB)
			.start(step)
			.repository(jobRepository)
			.incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean(name = PREFIX + STEP)
	@JobScope
	public Step step(
		@Qualifier(PREFIX + EXECUTOR) TaskExecutor taskExecutor,
		@Qualifier(PREFIX + READER) ItemReader itemReader,
		@Qualifier(PREFIX + PROCESSOR) ItemProcessor itemProcessor,
		@Qualifier(PREFIX + WRITER) ItemWriter itemWriter) {
		return new StepBuilder(PREFIX + STEP)
			.repository(jobRepository)
			.transactionManager(transactionManager)
			.<Pocket, Pocket>chunk(CHUNK_SIZE)
			.reader(itemReader)
			.processor(itemProcessor)
			.writer(itemWriter)
			.faultTolerant()
			.retryLimit(RETRY_LIMIT)
			.retry(Exception.class)
			.taskExecutor(taskExecutor)
			.build();
	}

	@Bean(name = PREFIX + READER)
	@StepScope
	public JpaPagingItemReader<Pocket> itemReader() {
		return new JpaPagingItemReaderBuilder<Pocket>()
			.name(PREFIX + READER)
			.entityManagerFactory(entityManagerFactory)
			.pageSize(CHUNK_SIZE)
			.queryString("select p from Pocket p order by p.createdDate ASC")
			.build();
	}

	@Bean(name = PREFIX + PROCESSOR)
	@StepScope
	public ItemProcessor<Pocket, Pocket> itemProcessor() {
		return pocket -> {
			LocalDateTime createdDate = pocket.getCreatedDate();

			if (createdDate.isBefore(LocalDateTime.now().minusMonths(1))) {
				return pocket;
			}
			return null;
		};
	}

	@Bean(name = PREFIX + WRITER)
	@StepScope
	public ItemWriter<Pocket> itemWriter() {
		return chunk -> {
			for (Pocket pocket : chunk) {
				String email = pocket.getEmail();
				String mailSubject = "[Gogonew] 목표 설정 한달이 지나 메일드립니다.";
				String mailText = createMailText(pocket);

				emailRepository.sendEmail(email, mailSubject, mailText);
			}
		};
	}

	private String createMailText(Pocket pocket) {
		StringBuilder sb = new StringBuilder();
		sb.append("[Gogonew 알람 메일] \n\n");
		sb.append("안녕하세요 :) \n\n");
		sb.append("목표는 잘 이루고 계신가요?\n");
		sb.append("목표 설정 후 1달이 지나 메일 드립니다.\n");
		sb.append("설정하신 목표는 다음과 같습니다.\n\n");

		for (Goal goal : pocket.getGoals()) {
			sb.append(goal.getTodo());
			sb.append("\n");
		}

		sb.append("\n");
		sb.append("목표 달성하시느라 정말 고생 많으셨어요.\n");
		sb.append("남은 목표들도 모두 이루어시길 응원하겠습니다.\n\n");
		sb.append("감사합니다.");

		return sb.toString();
	}
}
