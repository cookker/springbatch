//package io.spring.helloworld.sec7lec30;
//
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.ItemWriter;
//
//import java.util.List;
//
//@Slf4j
//public class RetryItemWriter implements ItemWriter<String> {
//    @Setter
//    private boolean retry = false;
//    private int attemptCount = 0;
//
//    @Override
//    public void write(List<? extends String> items) throws Exception {
//        for (String item : items) {
//            log.info("writing item: {}", item);
//            if(retry && item.equalsIgnoreCase("-84")) {
//                attemptCount++;
//
//                if(attemptCount >= 5) {
//                    log.info("Success! {}", item);
//                    retry = false;
//                }
//                else {
//                    log.error("Writing of item {}, failed", item);
//                    throw new CustomRetryableException("Write failed.  Attempt:" + attemptCount);
//                }
//            }
//            else {
//                log.info("{}", item);
//            }
//        }
//    }
//}
