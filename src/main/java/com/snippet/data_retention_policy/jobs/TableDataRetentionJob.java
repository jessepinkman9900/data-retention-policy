package com.snippet.data_retention_policy.jobs;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TableDataRetentionJob {
  private static final Logger logger = LoggerFactory.getLogger(TableDataRetentionJob.class);
  PlatformTransactionManager transactionManager;
  ItemReader<Map<String, Object>> tableItemReader;

  @Autowired
  public TableDataRetentionJob(
      PlatformTransactionManager transactionManager,
      ItemReader<Map<String, Object>> tableItemReader) {
    this.transactionManager = transactionManager;
    this.tableItemReader = tableItemReader;
  }

  // ****
  // Job
  // ****
  @Bean
  public Job tableRetentionJob(JobRepository jobRepository) {
    return new JobBuilder("tableRetentionJob", jobRepository)
        .start(tableRetentionStep(jobRepository))
        .build();
  }

  @Bean
  public Step tableRetentionStep(JobRepository jobRepository) {
    return new StepBuilder("tableReader", jobRepository)
        .<Map<String, Object>, Map<String, Object>>chunk(10, transactionManager)
        .reader(tableItemReader)
        .processor(
            item -> {
              logger.info("Processing item: " + item);
              return item;
            }) // Pass through processor
        .writer(
            items -> {

              // TODO: Implement delete logic
            })
        .build();
  }
}
