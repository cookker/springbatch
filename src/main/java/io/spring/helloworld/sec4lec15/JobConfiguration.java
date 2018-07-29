//package io.spring.helloworld.sec4lec15;
//
//import io.spring.helloworld.sec4lec14.Customer;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//
//    @Bean
//    public FlatFileItemReader<Customer> customerItemReader(){
//        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
//
//        reader.setLinesToSkip(1);
//        reader.setResource(new ClassPathResource("/data/customer.csv"));
//
//        DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setNames(new String[]{"id", "firstName", "lastName", "birthdate"});
//
//        customerLineMapper.setLineTokenizer(tokenizer);
//        customerLineMapper.setFieldSetMappe r(new CustomerFieldSetMapper());
//        customerLineMapper.afterPropertiesSet();
//
//        reader.setLineMapper(customerLineMapper);
//        return reader;
//    }
//
//    @Bean
//    public ItemWriter<Customer> customerItemWriter(){
//        return items -> {
//          items.forEach(i -> log.info("current Item:{}", i));
//        };
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .<Customer, Customer>chunk(10)
//                .reader(customerItemReader())
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job jobCsv(){
//        return jobBuilderFactory.get("jobCsv")
//                .start(step1())
//                .build();
//    }
//}
