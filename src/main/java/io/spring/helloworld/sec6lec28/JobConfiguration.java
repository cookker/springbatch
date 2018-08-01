//package io.spring.helloworld.sec6lec28;
//
//import io.spring.helloworld.sec4lec14.Customer;
//import io.spring.helloworld.sec4lec14.CustomerRowMapper;
//import io.spring.helloworld.sec5lec22.CustomerLineAggregator;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.support.CompositeItemProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
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
//    public JdbcPagingItemReader<Customer> pagingItemReader(){
//        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
//        reader.setDataSource(dataSource);
//        reader.setFetchSize(10);
//        reader.setRowMapper(new CustomerRowMapper());
//
//        MySqlPagingQueryProvider pagingQueryProvider = new MySqlPagingQueryProvider();
//        pagingQueryProvider.setSelectClause("id, firstName, lastName, birthdate");
//        pagingQueryProvider.setFromClause("from customer");
//        pagingQueryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));
//
//        reader.setQueryProvider(pagingQueryProvider);
//        return reader;
//    }
//
//    @Bean
//    public FlatFileItemWriter<Customer> jsonItemWriter() throws Exception {
//        FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
////        writer.setLineAggregator(new PassThroughLineAggregator<>());
//        writer.setLineAggregator(new CustomerLineAggregator());
//        String customerOutputPath = File.createTempFile("customer", ".out").getAbsolutePath();
//        log.info(">> outputPath: {}", customerOutputPath);
//        writer.setResource(new FileSystemResource(customerOutputPath));
//        writer.afterPropertiesSet();
//        return writer;
//    }
//
//    @Bean
//    public CompositeItemProcessor<Customer, Customer> itemProcessor() throws Exception {
//        List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>(2);
//        delegates.add(new FilteringItemProcessor());
//        delegates.add(new UpperCaseItemProcessor());
//
//        CompositeItemProcessor<Customer, Customer> compositeItemProcessor = new CompositeItemProcessor<>();
//        compositeItemProcessor.setDelegates(delegates);
//        compositeItemProcessor.afterPropertiesSet();
//
//        return compositeItemProcessor;
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//                .<Customer, Customer>chunk(10)
//                .reader(pagingItemReader())
//                .processor(itemProcessor())
//                .writer(jsonItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job compositeProcessorJob() throws Exception {
//        return jobBuilderFactory.get("compositeProcessorJob")
//                .start(step1())
//                .build();
//    }
//
//}
