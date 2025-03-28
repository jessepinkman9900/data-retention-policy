package com.snippet.data_retention_policy.jobs;

import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class ItemReader {

  @Bean
  @StepScope
  public JdbcPagingItemReader<Map<String, Object>> tableItemReader(
      DataSource dataSource,
      @Value("#{jobParameters['batch_size']}") Long batchSize,
      @Value("#{jobParameters['schema_name']}") String schemaName,
      @Value("#{jobParameters['table_name']}") String tableName,
      @Value("#{jobParameters['timestamp_column']}") String timestampColumn,
      @Value("#{jobParameters['start_time']}") LocalDateTime startTime) {

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
}
