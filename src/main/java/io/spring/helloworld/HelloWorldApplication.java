package io.spring.helloworld;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@IntegrationComponentScan
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

//    @Bean
//    public JobLauncherTestUtils utils(){
//        return new JobLauncherTestUtils();
//    }
}
