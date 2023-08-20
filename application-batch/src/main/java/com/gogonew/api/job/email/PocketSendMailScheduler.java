package com.gogonew.api.job.email;

import static com.gogonew.api.common.BatchComponent.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PocketSendMailScheduler {
	private static final String PREFIX = "PocketSendMail";

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	@Scheduled(cron = "0 0 10 * * *") // 매일 10:00 수행
	public void sendMailToOverOneMonthPocket() throws
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		Job job = jobRegistry.getJob(PREFIX + JOB);

		Map<String, JobParameter> jobParamMap = new HashMap<>();
		jobParamMap.put("executeTime", new JobParameter(String.valueOf(LocalDateTime.now())));
		JobParameters jobParameters = new JobParameters(jobParamMap);

		jobLauncher.run(job, jobParameters);
	}
}
