package io.spring.helloworld.sec4lec14;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@RequiredArgsConstructor
@ToString
@Setter
@Getter
public class Customer {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;
}