package io.spring.helloworld.sec6lec27;

import io.spring.helloworld.sec4lec14.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

@Slf4j
public class CustomerValidator implements Validator<Customer> {
    @Override
    public void validate(Customer value) throws ValidationException {
        if(value.getFirstName().startsWith("A")){
            log.error("firstName that begin with A are invalid, value={}", value);
            throw new ValidationException("이름이 A로 시작하다니..");
        }
    }
}
