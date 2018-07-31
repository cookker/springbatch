package io.spring.helloworld.sec6lec28;

import io.spring.helloworld.sec4lec14.Customer;
import org.springframework.batch.item.ItemProcessor;

public class FilteringItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        return item.getId()%2 == 0 ? null : item;
    }
}
