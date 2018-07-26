package io.spring.helloworld.sec3lec9.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatchConfiguration {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private static final String ODD = "ODD";
    private static final String EVEN = "EVEN";


    @Bean
    public Step startStep(){
        return stepBuilderFactory.get("startStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("this is the start tasklet");
                    return RepeatStatus.FINISHED;
                }))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step evenStep(){
        return stepBuilderFactory.get("evenStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("this is the even tasklet");
                    return RepeatStatus.FINISHED;
                }))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step oddStep(){
        return stepBuilderFactory.get("oddStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("this is the odd tasklet");
                    return RepeatStatus.FINISHED;
                }))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public JobExecutionDecider decider(){
        return new OddDecider();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(startStep())
                .next(decider())
                .from(decider()).on(ODD).to(oddStep())
                .from(decider()).on(EVEN).to(evenStep())
                .from(oddStep()).on("*").to(decider())
                //이부분을 주석처리해도 결과는 같게 나온다.
                /**
                결과
                : Executing step: [startStep]
                : this is the start tasklet
                : Executing step: [oddStep]
                : this is the odd tasklet
                : Executing step: [evenStep]
                : this is the even tasklet
                **/
//                .from(decider()).on(ODD).to(oddStep())
//                .from(decider()).on(EVEN).to(evenStep())
                .end()
                .build();
    }

    public static class OddDecider implements JobExecutionDecider{
        private int count = 0;

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            String evenOrOdd = ++count%2 == 0 ? EVEN : ODD;
            return new FlowExecutionStatus(evenOrOdd);
        }
    }
}
