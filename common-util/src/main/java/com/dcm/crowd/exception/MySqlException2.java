package com.dcm.crowd.exception;

public class MySqlException2 extends MySqlException {//update异常
    public MySqlException2() {
    }

    public MySqlException2(String message) {
        super(message);
    }

    public MySqlException2(String message, Throwable cause) {
        super(message, cause);
    }

    public MySqlException2(Throwable cause) {
        super(cause);
    }
}
