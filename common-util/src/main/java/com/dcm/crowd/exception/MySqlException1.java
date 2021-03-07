package com.dcm.crowd.exception;

public class MySqlException1 extends MySqlException {//save异常
    public MySqlException1() {
    }

    public MySqlException1(String message) {
        super(message);
    }

    public MySqlException1(String message, Throwable cause) {
        super(message, cause);
    }

    public MySqlException1(Throwable cause) {
        super(cause);
    }
}
