package io.spring.helloworld.sec9lec40;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JobLaunchingController {
    private final JobOperator jobOperator;

    @PostMapping("/")
    public long launch(@RequestParam("name") String name) throws Exception{
        return this.jobOperator.start("job", String.format("name=%s", name));
    }

    @DeleteMapping("/{id}")
    public void stop(@PathVariable("id") long id) throws Exception{
        this.jobOperator.stop(id);
    }
}
