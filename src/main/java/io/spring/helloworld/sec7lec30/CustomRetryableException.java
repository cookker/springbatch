package io.spring.helloworld.sec7lec30;

public class CustomRetryableException extends RuntimeException {
    public CustomRetryableException() {
        super();
    }

    public CustomRetryableException(String message) {
        super(message);
    }
}
