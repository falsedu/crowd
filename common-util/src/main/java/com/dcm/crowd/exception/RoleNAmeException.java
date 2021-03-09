package com.dcm.crowd.exception;

public class RoleNAmeException extends RuntimeException {
    public RoleNAmeException() {
    }

    public RoleNAmeException(String message) {
        super(message);
    }

    public RoleNAmeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNAmeException(Throwable cause) {
        super(cause);
    }
}
