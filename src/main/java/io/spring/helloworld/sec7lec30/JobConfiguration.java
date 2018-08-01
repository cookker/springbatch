//package io.spring.helloworld.sec7lec30;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//
//    @Bean
//    @StepScope
//    public ListItemReader<String> reader(){
//        return new ListItemReader<>(IntStream.rangeClosed(0, 99)
//                .boxed()
//                .map(String::valueOf)
//                .collect(Collectors.toList()));
//    }
//
//    @Bean
//    @StepScope
//    public RetryItemProcessor processor(@Value("#{jobParameters['retry']}")String retry) {
//        RetryItemProcessor processor = new RetryItemProcessor();
//
//        processor.setRetry(StringUtils.hasText(retry) && retry.equalsIgnoreCase("processor"));
//        return processor;
//    }
//
//    @Bean
//    @StepScope
//    public RetryItemWriter writer(@Value("#{jobParameters['retry']}")String retry) {
//        RetryItemWriter writer = new RetryItemWriter();
//
//        writer.setRetry(StringUtils.hasText(retry) && retry.equalsIgnoreCase("writer"));
//        return writer;
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .<String, String>chunk(10)
//                .reader(reader())
//                .processor(processor(null))
//                .writer(writer(null))
//                .faultTolerant()
//                .retry(CustomRetryableException.class)
//                .retryLimit(15)
//                .build();
//    }
//
//    @Bean
//    public Job retryJob(){
//        return jobBuilderFactory.get("retryJob")
//                .start(step1())
//                .build();
//    }
//
//}
