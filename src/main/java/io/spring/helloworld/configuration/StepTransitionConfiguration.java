package io.spring.helloworld.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StepTransitionConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> This is step1");
                    return RepeatStatus.FINISHED;
                })
//                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2(){
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> This is step2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3(){
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> This is step3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

//    @Bean
//    public Job helloWorldJob(){
//        return jobBuilderFactory.get("helloWorldJob")
//                .start(step1())
//                .build();
//    }

    @Bean
    public Job transitionSimpleJobNext(){
        return jobBuilderFactory.get("transitionSimpleJobNext")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }
}
