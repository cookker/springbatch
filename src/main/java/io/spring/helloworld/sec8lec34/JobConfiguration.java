//package io.spring.helloworld.sec8lec34;
//
//import io.spring.helloworld.sec4lec14.Customer;
//import io.spring.helloworld.sec4lec14.CustomerRowMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.integration.async.AsyncItemProcessor;
//import org.springframework.batch.integration.async.AsyncItemWriter;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//
//import javax.sql.DataSource;
//import java.util.Collections;
//import java.util.Random;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//    private final DataSource dataSource;
//
//    @Bean
//    public JdbcPagingItemReader<Customer> pagingItemReader() {
//        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
//        reader.setDataSource(dataSource);
//        reader.setFetchSize(1000);
//        reader.setRowMapper(new CustomerRowMapper());
//
//        MySqlPagingQueryProvider pagingQueryProvider = new MySqlPagingQueryProvider();
//        pagingQueryProvider.setSelectClause("id, firstName, lastName, birthdate");
//        pagingQueryProvider.setFromClause("from customer");
//        pagingQueryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));
//
//        reader.setQueryProvider(pagingQueryProvider);
//        reader.setSaveState(false);
//        return reader;
//    }
//
//    @Bean
//    public ItemProcessor<Customer, Customer> itemProcessor() {
//        return item -> {
//            Thread.sleep(new Random().nextInt(10));
//            return item.toUpper();
//        };
//    }
//
//    @Bean
//    public AsyncItemProcessor asyncItemProcessor() throws Exception {
//        AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
//        asyncItemProcessor.setDelegate(itemProcessor());
//        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
//        asyncItemProcessor.afterPropertiesSet();
//        return asyncItemProcessor;
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Customer> customerItemWriter(){
//        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(this.dataSource);
//        writer.setSql("INSERT INTO CUSTOMER3 VALUES(:id, :firstName, :lastName, :birthdate)");
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        writer.afterPropertiesSet();
//        return writer;
//    }
//
//    @Bean
//    public AsyncItemWriter asyncItemWriter() throws Exception {
//        AsyncItemWriter<Customer> asyncItemWriter = new AsyncItemWriter<>();
//        asyncItemWriter.setDelegate(customerItemWriter());
//        asyncItemWriter.afterPropertiesSet();
//
//        return asyncItemWriter;
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//                .<Customer, Customer>chunk(1000)
//                .reader(pagingItemReader())
//                .processor(asyncItemProcessor())
//                .writer(asyncItemWriter())
////                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .build();
//    }
//    @Bean
//    public Job multithreadjob() throws Exception {
//        return jobBuilderFactory.get("multithreadjob7")
//                .start(step1())
//                .build();
//    }
//}
