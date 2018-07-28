//package io.spring.helloworld.sec4lec14;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Collections;
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
//    public JdbcCursorItemReader<Customer> cursorItemReader(){
//        JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
//        reader.setSql("select id, firstName, lastName, birthdate from customer order by lastName, firstName");
//        reader.setDataSource(dataSource);
//        reader.setRowMapper(new CustomerRowMapper());
//        return reader;
//    }
//
//    @Bean
//    public JdbcPagingItemReader<Customer> pagingItemReader(){
//        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
//        reader.setDataSource(dataSource);
//        reader.setFetchSize(10);
//        reader.setRowMapper(new CustomerRowMapper());
//
//        MySqlPagingQueryProvider pagingQueryProvider = new MySqlPagingQueryProvider();
//        pagingQueryProvider.setSelectClause("id, firstName, lastName, birthdate");
//        pagingQueryProvider.setFromClause("from customer");
//        pagingQueryProvider.setSortKeys(Collections.singletonMap("id", Order.DESCENDING));
//
//        reader.setQueryProvider(pagingQueryProvider);
//        return reader;
//    }
//
//    @Bean
//    public ItemWriter<Customer> customerItemWriter(){
//        return items -> {
//          items.forEach(i -> log.info("current Item: {}", i));
//        };
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .<Customer, Customer>chunk(10)
//                .reader(pagingItemReader())
////                .reader(cursorItemReader())
//                .writer(customerItemWriter())
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public Job job(){
//        return jobBuilderFactory.get("job")
//                .start(step1())
//                .build();
//    }
//
//}
