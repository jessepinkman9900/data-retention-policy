package com.snippet.data_retention_policy.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TableDataRetentionJob {
  PlatformTransactionManager transactionManager;

  @Autowired
  public TableDataRetentionJob(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
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
    // return new StepBuilder("tableRetentionStep", jobRepository)
    //     .reader(tableItemReader())
    //     .build();
    return new StepBuilder("step1", jobRepository)
        .tasklet((contribution, chunkContext) -> null, transactionManager)
        .build();
  }

  // ****
  // Reader
  // ****

  // @Bean
  // public TableRetentionReader tableRetentionReader() {
  //     return new TableRetentionReader();
  // }
}
