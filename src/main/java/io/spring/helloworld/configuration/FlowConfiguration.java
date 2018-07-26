//package io.spring.helloworld.configuration;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class FlowConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//
//    private static final String COMPLETED = "COMPLETED";
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("Step 1 from inside flow foo");
//                    return RepeatStatus.FINISHED;
//                })
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public Step step2(){
//        return stepBuilderFactory.get("step2")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("Step 2 from inside flow foo");
//                    return RepeatStatus.FINISHED;
//                })
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public Flow foo(){
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("foo");
//        flowBuilder.start(step1())
//                .next(step2())
//                .end();
//        return flowBuilder.build();
//    }
//
//}
