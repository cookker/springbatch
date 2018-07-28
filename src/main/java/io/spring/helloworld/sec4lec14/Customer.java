package io.spring.helloworld.sec4lec14;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@RequiredArgsConstructor
@ToString
public class Customer {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;
}