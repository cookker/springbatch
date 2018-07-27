package io.spring.helloworld.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class ChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext){
        log.info("{}, {}", ">> Before the chunk", chunkContext.getStepContext().getStepName());
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext){
        log.info("{}, {}", "<< After the chunk", chunkContext.getStepContext().getStepName());
    }
}
