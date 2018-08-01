package io.spring.helloworld.sec7lec32;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SkipItemWriter implements ItemWriter<String> {
    private int attemptCount = 0;

    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
            if(item.equalsIgnoreCase("-84")) {
                throw new CustomException("Write failed.  Attempt:" + attemptCount);
            }
            else {
                log.info("{}", item);
            }
        }
    }
}
