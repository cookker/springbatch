package io.spring.helloworld.sec5lec22;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.helloworld.sec4lec14.Customer;
import org.springframework.batch.item.file.transform.LineAggregator;

public class CustomerLineAggregator implements LineAggregator<Customer> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String aggregate(Customer item) {
        try {
            return objectMapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("이제 그만~" , e);
        }
    }
}
