package com.snippet.data_retention_policy.jobs;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TableDataRetentionJob {
  private static final Logger logger = LoggerFactory.getLogger(TableDataRetentionJob.class);
  PlatformTransactionManager transactionManager;

  // ItemReader<Map<String, Object>> tableItemReader;
  // ItemWriter<Map<String, Object>> tableItemWriter;

  @Autowired
  public TableDataRetentionJob(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  // ****
  // Job
  // ****
  @Bean
  public Job tableRetentionJob(
      JobRepository jobRepository,
      ItemReader<Map<String, Object>> tableItemReader,
      ItemWriter<Map<String, Object>> tableItemWriter) {
    return new JobBuilder("tableRetentionJob", jobRepository)
        .start(tableRetentionStep(jobRepository, tableItemReader, tableItemWriter))
        .build();
  }

  @Bean
  public Step tableRetentionStep(
      JobRepository jobRepository,
      ItemReader<Map<String, Object>> tableItemReader,
      ItemWriter<Map<String, Object>> tableItemWriter) {
    return new StepBuilder("tableCleaner", jobRepository)
        .<Map<String, Object>, Map<String, Object>>chunk(10, transactionManager)
        .reader(tableItemReader)
        .processor(
            item -> {
              logger.info("Processing item: {} {}", item.getClass(), item);
              return item;
            }) // Pass through processor
        .writer(tableItemWriter)
        .build();
  }

  // ************
  // ItemReader
  // ************

  @Bean
  @StepScope
  public JdbcPagingItemReader<Map<String, Object>> tableItemReader(
      DataSource dataSource,
      @Value("#{jobParameters['batch_size']}") Long batchSize,
      @Value("#{jobParameters['schema_name']}") String schemaName,
      @Value("#{jobParameters['table_name']}") String tableName,
      @Value("#{jobParameters['timestamp_column_name']}") String timestampColumn,
      @Value("#{jobParameters['start_timestamp']}") LocalDateTime startTime) {

    SqlPagingQueryProviderFactoryBean providerFactory = new SqlPagingQueryProviderFactoryBean();
    providerFactory.setDataSource(dataSource);
    providerFactory.setSelectClause("select *");
    providerFactory.setFromClause("from " + schemaName + "." + tableName);
    providerFactory.setWhereClause("where " + timestampColumn + " <= '" + startTime + "'");
    providerFactory.setSortKey(timestampColumn);

    try {
      PagingQueryProvider queryProvider = providerFactory.getObject();
      return new JdbcPagingItemReaderBuilder<Map<String, Object>>()
          .name("tableItemReader")
          .dataSource(dataSource)
          .queryProvider(queryProvider)
          .pageSize(batchSize.intValue())
          .rowMapper(
              (RowMapper<Map<String, Object>>)
                  (rs, rowNum) -> {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Map<String, Object> row = new HashMap<>();

                    for (int i = 1; i <= columnCount; i++) {
                      String columnName = metaData.getColumnName(i);
                      Object value = rs.getObject(i);
                      row.put(columnName, value);
                    }

                    return row;
                  })
          .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create query provider", e);
    }
  }

  // ************
  // ItemWriter
  // ************

  @Bean
  @StepScope
  public JdbcBatchItemWriter<Map<String, Object>> tableItemWriter(
      DataSource dataSource,
      @Value("#{jobParameters['schema_name']}") String schemaName,
      @Value("#{jobParameters['table_name']}") String tableName,
      @Value("#{jobParameters['id_column_name']}") String idColumnName) {
    logger.info("Creating item writer for table: {}", tableName);
    String sql =
        String.format("DELETE FROM %s.%s WHERE %s = :id", schemaName, tableName, idColumnName);
    return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
        .dataSource(dataSource)
        .sql(sql)
        .itemPreparedStatementSetter(new IdTableSetter(idColumnName))
        .assertUpdates(true)
        .build();
  }

  public class IdTableSetter implements ItemPreparedStatementSetter<Map<String, Object>> {
    private final String idColumnName;

    public IdTableSetter(String idColumnName) {
      this.idColumnName = idColumnName;
    }

    @Override
    public void setValues(Map<String, Object> item, PreparedStatement ps) throws SQLException {
      logger.info("Writer object: " + item);
      if (item.get(idColumnName) == null) {
        throw new IllegalArgumentException("ID column name " + idColumnName + " is invalid");
      }
      ps.setObject(1, item.get(idColumnName));
    }
  }
}
