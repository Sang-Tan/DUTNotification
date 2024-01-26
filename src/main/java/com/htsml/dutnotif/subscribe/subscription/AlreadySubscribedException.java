package com.htsml.dutnotif.subscribe.subscription;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException() {
        super();
    }

    public AlreadySubscribedException(String message) {
        super(message);
    }
}
