package io.spring.helloworld.sec7lec32;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfiguration {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    @StepScope
    public ListItemReader<String> reader(){
        return new ListItemReader<>(IntStream.rangeClosed(0, 99)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList()));
    }

    @Bean
    @StepScope
    public SkipItemProcessor processor() {
        return new SkipItemProcessor();
    }

    @Bean
    @StepScope
    public SkipItemWriter writer() {
        return new SkipItemWriter();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step")
                .<String, String>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skip(CustomException.class)
                .skipLimit(15)
                .listener(new CusmtomSkipListener())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
