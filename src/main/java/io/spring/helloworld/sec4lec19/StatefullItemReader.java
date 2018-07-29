//package io.spring.helloworld.sec4lec19;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.*;
//
//import java.util.List;
//
//@Slf4j
//public class StatefullItemReader implements ItemStreamReader<String> {
//    private final List<String> items;
//    private int curIndex = -1;
//    private boolean restart = false;
//
//    public StatefullItemReader(List<String> items) {
//        this.items = items;
//        this.curIndex = 0;
//    }
//
//    @Override
//    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        String item = null;
//
//        if(this.curIndex < this.items.size()){
//            item = this.items.get(this.curIndex);
//            this.curIndex++;
//        }
//
//        if(this.curIndex == 46 && !restart){
//            throw new RuntimeException("일부러 에러를 내보세.");
//
//        }
//        return item;
//    }
//
//    @Override
//    public void open(ExecutionContext executionContext) throws ItemStreamException {
//        log.info("open호출, curIndex:{}", curIndex);
//        if(executionContext.containsKey("curIndex")){
//            this.curIndex = executionContext.getInt("curIndex");
//            this.restart = true;
//        }else{
//            this.curIndex = 0;
//            executionContext.put("curIndex", this.curIndex);
//        }
//    }
//
//    @Override
//    public void update(ExecutionContext executionContext) throws ItemStreamException {
//        log.info("update호출, curIndex:{}", curIndex);
//        executionContext.put("curIndex", this.curIndex);
//    }
//
//    @Override
//    public void close() throws ItemStreamException {
//    }
//}
