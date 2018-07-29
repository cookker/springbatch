//package io.spring.helloworld.sec4lec19;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class JobConfiguration {
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    @StepScope
//    public StatefullItemReader itemReader(){
//        return new StatefullItemReader(IntStream
//                .rangeClosed(1, 100)
//                .boxed()
//                .map(String::valueOf)
//                .collect(Collectors.toList()));
//    }
//
//    @Bean
//    public ItemWriter<String> itemWriter(){
//        return items -> {
//          items.forEach(i -> log.info(">> {}", i));
//        };
//    }
//
//    @Bean
//    public Step step(){
//        return stepBuilderFactory.get("step")
//                .<String, String>chunk(10)
//                .reader(itemReader())
//                .writer(itemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job statefulJob(){
//        return jobBuilderFactory.get("statefulJob4")
//                .start(step())
//                .build();
//    }
//}
