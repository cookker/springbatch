package io.spring.helloworld.sec7lec32;

public class CustomException extends Exception {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}
