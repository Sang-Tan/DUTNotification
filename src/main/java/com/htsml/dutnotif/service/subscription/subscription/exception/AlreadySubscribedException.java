package com.htsml.dutnotif.service.subscription.subscription.exception;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException() {
        super();
    }

    public AlreadySubscribedException(String message) {
        super(message);
    }
}
