package io.spring.helloworld.sec8lec36;

import io.spring.helloworld.sec4lec14.Customer;
import io.spring.helloworld.sec4lec14.CustomerRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfiguration {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public ColumnRangeParitioner paritioner(){
        ColumnRangeParitioner columnRangeParitioner = new ColumnRangeParitioner();
        columnRangeParitioner.setColumn("id");
        columnRangeParitioner.setTable("customer");
        columnRangeParitioner.setDataSource(this.dataSource);
        return columnRangeParitioner;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Customer> pagingItemReader(@Value("#{stepExecutionContext['minValue']}") Long minValue,
                                                           @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
        log.info("minValue:{}, maxValue:{}", minValue, maxValue);
        JdbcPagingItemReader<Customer> jdbcPagingItemReader = new JdbcPagingItemReader<>();
        jdbcPagingItemReader.setDataSource(this.dataSource);
        jdbcPagingItemReader.setFetchSize(100);
        jdbcPagingItemReader.setRowMapper(new CustomerRowMapper());

        MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
        mySqlPagingQueryProvider.setSelectClause("id, firstName, lastName, birthdate");
        mySqlPagingQueryProvider.setFromClause("from customer");
        mySqlPagingQueryProvider.setWhereClause("where id >= " + minValue + " and id <= " + maxValue);
        mySqlPagingQueryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));

        jdbcPagingItemReader.setQueryProvider(mySqlPagingQueryProvider);
//        try {
//            jdbcPagingItemReader.afterPropertiesSet();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return jdbcPagingItemReader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Customer> customerItemWriter(){
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO CUSTOMER2 VALUES (:id, :firstName, :lastName, :birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .partitioner(slaveStep().getName(), paritioner())
                .step(slaveStep())
                .gridSize(3)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step slaveStep(){
        return stepBuilderFactory.get("slaveStep")
                .<Customer, Customer>chunk(100)
                .reader(pagingItemReader(null, null))
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public Job partitionJob(){
        return jobBuilderFactory.get("partitionJob4")
                .start(step1())
                .build();
    }
}
