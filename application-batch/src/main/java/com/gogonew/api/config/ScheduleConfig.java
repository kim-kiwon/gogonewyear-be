package com.gogonew.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
	private final ThreadPoolTaskScheduler taskScheduler;

	ScheduleConfig() {
		taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setErrorHandler(t -> log.error("Exception in @Scheduled task. ", t));
		taskScheduler.setThreadNamePrefix("@scheduled-");

		taskScheduler.initialize();
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler);
	}
}
