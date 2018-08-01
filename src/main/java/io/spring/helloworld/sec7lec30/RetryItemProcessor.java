package io.spring.helloworld.sec7lec30;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RetryItemProcessor implements ItemProcessor<String, String> {
    @Setter
    private boolean retry = false;
    private int attemptCount = 0;

    @Override
    public String process(String item) throws Exception {
        log.info("processing item: {}", item);
        if(retry && "42".equalsIgnoreCase(item)){
            attemptCount++;

            if(attemptCount >= 5){
                log.info("Success!");
                retry = false;
                return String.valueOf(Integer.valueOf(item) * -1);
            }else{
                log.error("Processing of item: {} is failed.", item);
                throw new CustomRetryableException("Process failed. Attempt:" + attemptCount);
            }
        }else{
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
