package io.spring.helloworld.sec7lec32;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SkipItemProcessor implements ItemProcessor<String, String> {
    private int attempCount = 0;

    @Override
    public String process(String item) throws Exception {
        if(Integer.valueOf(item) % 3 == 0){
            attempCount++;
            log.error("Process was failed. AttemptCount:{}",  attempCount);
            throw new CustomException("Process was failed. AttemptCount:" + attempCount);
        }else{
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
