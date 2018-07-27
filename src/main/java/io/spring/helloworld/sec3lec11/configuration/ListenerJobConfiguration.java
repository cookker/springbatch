package io.spring.helloworld.sec3lec11.configuration;

import io.spring.helloworld.listener.ChunkListener;
import io.spring.helloworld.listener.JobListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.Properties;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ListenerJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<String> reader(){
        return new ListItemReader<>(Arrays.asList("one", "two", "three"));
    }

    @Bean
    public ItemWriter<String> writer(){
        return items -> {
            for(String item : items){
                log.info("Writing item: {}", item);
            }
        };
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(new ChunkListener())
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("i3rewmaster@gmail.com");
        mailSender.setPassword("내비밀번호");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public Job listenerJob(JavaMailSender javaMailSender){
        return jobBuilderFactory.get("listenerJob")
                .start(step1())
                .listener(new JobListener(javaMailSender))
                .build();

    }

}
