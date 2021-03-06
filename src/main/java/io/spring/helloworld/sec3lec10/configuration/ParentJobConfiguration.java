//package io.spring.helloworld.sec3lec10.configuration;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.JobStepBuilder;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class ParentJobConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//    private final Job childJob;
//    private final JobLauncher jobLauncher;
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("This is step 1");
//                    return RepeatStatus.FINISHED;
//                })
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
//        Step childJobStep = new JobStepBuilder(new StepBuilder("childJobStep"))
//                .job(childJob)
//                .launcher(jobLauncher)
//                .repository(jobRepository)
//                .transactionManager(platformTransactionManager)
//                .build();
//        return jobBuilderFactory.get("parentJob")
//                .start(step1())
//                .next(childJobStep)
//                .build();
//    }
//}
