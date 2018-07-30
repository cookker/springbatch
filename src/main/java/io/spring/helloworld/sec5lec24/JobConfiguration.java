//package io.spring.helloworld.sec5lec24;
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
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
//import org.springframework.batch.item.support.CompositeItemWriter;
//import org.springframework.batch.item.xml.StaxEventItemWriter;
//import org.springframework.classify.Classifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.oxm.xstream.XStreamMarshaller;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.sql.Date;
//import java.util.*;
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
//    public StaxEventItemWriter<Customer> xmlItemWriter() throws Exception {
//        XStreamMarshaller marshaller = new XStreamMarshaller();
//
//        Map<String, Class> aliasMap = new HashMap<>();
//        aliasMap.put("customer", Customer.class);
//        aliasMap.put("birthdate", Date.class);
//        marshaller.setAliases(aliasMap);
//
//        StaxEventItemWriter<Customer> itemWriter = new StaxEventItemWriter<>();
//        itemWriter.setRootTagName("customers");
//        itemWriter.setMarshaller(marshaller);
//        String customerOutputPath = File.createTempFile("customer", ".xml").getAbsolutePath();
//        log.info(">> output path: {}", customerOutputPath);
//        itemWriter.setResource(new FileSystemResource(customerOutputPath));
//        itemWriter.afterPropertiesSet();
//
//        return itemWriter;
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
//    public CompositeItemWriter<Customer> itemWriter() throws Exception {
//        List<ItemWriter<? super Customer>> writers = new ArrayList<>(2);
//        writers.add(xmlItemWriter());
//        writers.add(jsonItemWriter());
//
//        CompositeItemWriter<Customer> compositeItemWriter = new CompositeItemWriter<>();
//        compositeItemWriter.setDelegates(writers);
//        compositeItemWriter.afterPropertiesSet();
//
//        return compositeItemWriter;
//    }
//
//    @Bean
//    public ClassifierCompositeItemWriter<Customer> classifierCompositeItemWriter() throws Exception {
//        ClassifierCompositeItemWriter<Customer> itemWriter = new ClassifierCompositeItemWriter<>();
//        itemWriter.setClassifier(new CustomerClassifier(xmlItemWriter(), jsonItemWriter()));
//        return itemWriter;
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//                .<Customer, Customer>chunk(10)
//                .reader(pagingItemReader())
//                .writer(classifierCompositeItemWriter())
//                .stream(xmlItemWriter())
//                .stream(jsonItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job classifierJob() throws Exception {
//        return jobBuilderFactory.get("classifierJob2")
//                .start(step1())
//                .build();
//    }
//}
