//package io.spring.helloworld.sec4lec13.configuration;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public StatelessItemReader statelessItemReader(){
//        return new StatelessItemReader(Arrays.asList("111","222","333"));
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .<String, String>chunk(2)
//                .reader(statelessItemReader())
//                .writer(items -> {
//                    items.forEach(i -> log.info("currentItem: {}",i));
//                })
//                .build();
//    }
//
//    @Bean
//    public Job interfaceJob(){
//        return jobBuilderFactory.get("interfaceJob")
//                .start(step1())
//                .build();
//    }
//}
