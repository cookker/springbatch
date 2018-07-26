//package io.spring.helloworld.sec3lec8.configuration;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class BatchConfiguration {
//    private final StepBuilderFactory stepBuilderFactory;
//    private final JobBuilderFactory jobBuilderFactory;
//
//    @Bean
//    public Tasklet tasklet(){
//        return new CountingTasklet();
//    }
//
//    @Bean
//    public Flow flow1(){
//        return new FlowBuilder<Flow>("flow1")
//                .start(stepBuilderFactory.get("step1").allowStartIfComplete(true).tasklet(tasklet()).build())
//                .build();
//    }
//
//    @Bean
//    public Flow flow2(){
//        return new FlowBuilder<Flow>("flow2")
//                .start(stepBuilderFactory.get("step2").allowStartIfComplete(true).tasklet(tasklet()).build())
//                .next(stepBuilderFactory.get("step3").allowStartIfComplete(true).tasklet(tasklet()).build())
//                .build();
//    }
//
//    @Bean
//    public Job job(){
//        return jobBuilderFactory.get("job")
//                .start(flow1())
//                .split(new SimpleAsyncTaskExecutor()).add(flow2())
//                .end()
//                .build();
//    }
//
//    private class CountingTasklet implements Tasklet {
//        @Override
//        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//            log.info("CountingTasklet is called stepName: {}, ThreadName: {}", chunkContext.getStepContext().getStepName(), Thread.currentThread().getName());
//            return RepeatStatus.FINISHED;
//        }
//    }
//}
