package com.snippet.data_retention_policy;

import com.snippet.data_retention_policy.services.DataRetentionJobOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataRetentionPolicyApplication implements CommandLineRunner {
  private static final Logger logger =
      LoggerFactory.getLogger(DataRetentionPolicyApplication.class);
  private final DataRetentionJobOrchestrator dataRetentionJobOrchestrator;

  @Autowired
  public DataRetentionPolicyApplication(DataRetentionJobOrchestrator dataRetentionJobOrchestrator) {
    this.dataRetentionJobOrchestrator = dataRetentionJobOrchestrator;
  }

  public static void main(String[] args) {
    SpringApplication.run(DataRetentionPolicyApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Data retention policy job started");
    dataRetentionJobOrchestrator.run();
  }
}
