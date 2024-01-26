package com.htsml.dutnotif.subscribe.subscription;

public class InvalidSubjectException extends RuntimeException{

    public InvalidSubjectException() {
        super();
    }

    public InvalidSubjectException(String message) {
        super(message);
    }
}
