package com.snippet.data_retention_policy.configs;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Getter
@Configuration
@ConfigurationProperties(prefix = "retention-policy")
public class RetentionPolicyConfig {
  private Logger logger = LoggerFactory.getLogger(RetentionPolicyConfig.class);
  private DatabaseConfig database;

  @Data
  @Getter
  public static class DatabaseConfig {
    private String name;
    private DefaultConfigs defaultConfigs;
    private List<TableConfig> tables;
  }

  @Data
  @Getter
  public static class DefaultConfigs {
    private String tsColumnName;
    private Long batchSize = 1000L;
    private Long sleepBetweenDeleteBatchInMs = 1000L;
    private Long retentionPeriodInDays = 366L;
  }

  @Data
  @Getter
  public static class TableConfig {
    private String schemaName = null;
    private String tableName = null;
    private String timestampColumn = null;
    private String timestampDataType = null;
    private Long batchSize = -1L;
    private Long sleepBetweenDeleteBatchInMs = -1L;
    private Long retentionPeriodInDays = -1L;
  }

  @jakarta.annotation.PostConstruct
  private void init() {
    for (TableConfig tableConfig : database.getTables()) {
      logger.info(
          "Setting default values for table {}.{}",
          tableConfig.getSchemaName(),
          tableConfig.getTableName());
      if (tableConfig.getSchemaName() == null) {
        logger.error("Schema name is not specified for table");
      }
      if (tableConfig.getTableName() == null) {
        logger.error("Table name is not specified");
      }
      if (tableConfig.getTimestampColumn() == null) {
        logger.info(
            "Table {}.{} using database default configuration timestamp_column={}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            database.getDefaultConfigs().getTsColumnName());
        tableConfig.setTimestampColumn(database.getDefaultConfigs().getTsColumnName());
      }
      if (tableConfig.getBatchSize() == -1L) {
        logger.info(
            "Table {}.{} using database default configuration batch_size={}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            database.getDefaultConfigs().getBatchSize());
        tableConfig.setBatchSize(database.getDefaultConfigs().getBatchSize());
      }
      if (tableConfig.getSleepBetweenDeleteBatchInMs() == -1L) {
        logger.info(
            "Table {}.{} using database default configuration"
                + " sleep_between_delete_batch_in_ms={}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            database.getDefaultConfigs().getSleepBetweenDeleteBatchInMs());
        tableConfig.setSleepBetweenDeleteBatchInMs(
            database.getDefaultConfigs().getSleepBetweenDeleteBatchInMs());
      }
      if (tableConfig.getRetentionPeriodInDays() == -1L) {
        logger.info(
            "Table {}.{} using database default configuration" + " retention_period_in_days={}",
            tableConfig.getSchemaName(),
            tableConfig.getTableName(),
            database.getDefaultConfigs().getRetentionPeriodInDays());
        tableConfig.setRetentionPeriodInDays(
            database.getDefaultConfigs().getRetentionPeriodInDays());
      }
    }

    logger.info("RetentionPolicy configuration loaded");
    logger.info("Database name: {}", database.getName());
    logger.info("Default configuration: {}", database.getDefaultConfigs());
    for (TableConfig tableConfig : database.getTables()) {
      logger.info("Table configuration loaded: {}", tableConfig);
    }
  }
}
