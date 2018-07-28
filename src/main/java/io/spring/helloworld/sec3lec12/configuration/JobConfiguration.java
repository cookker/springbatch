//package io.spring.helloworld.sec3lec12.configuration;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
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
//    public Tasklet helloWorldTasklet(@Value("#{JobParameters['message']}") String message){
//        return (contribution, chunkContext) -> {
//            log.info("message:{}", message);
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .tasklet(helloWorldTasklet(null))
//                .build();
//    }
//
//    @Bean
//    public Job jobParametersJob(){
//        return jobBuilderFactory.get("jobParametersJob")
//                .start(step1())
//                .build();
//    }
//
//
//}
