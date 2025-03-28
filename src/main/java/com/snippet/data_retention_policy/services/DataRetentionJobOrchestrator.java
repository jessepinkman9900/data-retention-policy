package com.snippet.data_retention_policy.services;

import com.snippet.data_retention_policy.configs.RetentionPolicyConfig;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataRetentionJobOrchestrator {

  private final RetentionPolicyConfig retentionPolicyConfig;
  private final JobLauncher jobLauncher;
  private final Job tableRetentionJob;

  private static final Logger logger = LoggerFactory.getLogger(DataRetentionJobOrchestrator.class);

  @Autowired
  public DataRetentionJobOrchestrator(
      RetentionPolicyConfig retentionPolicyConfig, JobLauncher jobLauncher, Job tableRetentionJob) {
    this.retentionPolicyConfig = retentionPolicyConfig;
    this.jobLauncher = jobLauncher;
    this.tableRetentionJob = tableRetentionJob;
  }

  public void run() {
    System.out.println("Starting orchestrator");
    for (RetentionPolicyConfig.TableConfig tableConfig :
        retentionPolicyConfig.getDatabase().getTables()) {
      logger.info(
          "Processing table: {}.{}", tableConfig.getSchemaName(), tableConfig.getTableName());
      try {
        JobParameters jobParameters = buildJobParameters(tableConfig);
        JobExecution run = jobLauncher.run(tableRetentionJob, jobParameters);
        logger.info(
            "Job Instance for table {}.{}: {}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            run.getJobInstance());
      } catch (Exception e) {
        logger.error(
            "Failed to run job for table: {}.{}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            e);
        break;
      }
    }
  }

  private JobParameters buildJobParameters(RetentionPolicyConfig.TableConfig tableConfig) {
    return new JobParametersBuilder()
        .addString("schema_name", tableConfig.getSchemaName())
        .addString("table_name", tableConfig.getTableName())
        .addString("timestamp_column", tableConfig.getTimestampColumn())
        .addString("timestamp_data_type", tableConfig.getTimestampDataType())
        .addLong("batch_size", tableConfig.getBatchSize())
        .addLong("sleep_between_delete_batch_in_ms", tableConfig.getSleepBetweenDeleteBatchInMs())
        .addLong("retention_period_in_days", tableConfig.getRetentionPeriodInDays())
        .addLocalDateTime("start_time", ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())
        .toJobParameters();
  }
}
