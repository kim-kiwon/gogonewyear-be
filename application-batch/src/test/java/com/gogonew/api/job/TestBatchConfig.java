package com.gogonew.api.job;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class TestBatchConfig {
	@Primary
	@Bean
	@BatchDataSource
	public DataSource getDataSource() {
		return new EmbeddedDatabaseBuilder()
			.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
			.addScript("classpath:org/springframework/batch/core/schema-h2.sql")
			.setType(EmbeddedDatabaseType.H2)
			.build();
	}
}
