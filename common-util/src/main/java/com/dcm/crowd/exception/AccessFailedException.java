package com.dcm.crowd.exception;

public class AccessFailedException extends RuntimeException {

    private static final long serialVersionID=1L;


    public AccessFailedException() {
    }

    public AccessFailedException(String message) {
        super(message);
    }

    public AccessFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessFailedException(Throwable cause) {
        super(cause);
    }
}
