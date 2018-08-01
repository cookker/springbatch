package io.spring.helloworld.sec7lec32;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class CusmtomSkipListener implements SkipListener<String, String> {
    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {
        log.info(">>SKIP: {} in writing", item);
    }

    @Override
    public void onSkipInProcess(String item, Throwable t) {
        log.info(">>SKIP: {} in processing", item);
    }
}
