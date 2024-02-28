package com.htsml.dutnotif.service.subscription.subscription.exception;

public class InvalidSubjectException extends RuntimeException{

    public InvalidSubjectException() {
        super();
    }

    public InvalidSubjectException(String message) {
        super(message);
    }
}
