package com.make.backendroadmap.domain.exception;

public class FailToCallApiException extends RuntimeException {
    public FailToCallApiException(String message) {
        super(message);
    }

    public FailToCallApiException() {
        super();
    }
}
