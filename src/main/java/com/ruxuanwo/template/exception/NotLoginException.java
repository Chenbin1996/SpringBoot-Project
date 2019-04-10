package com.ruxuanwo.template.exception;

/**
 * 自定义登录异常
 *
 * @author ruxuanwo
 */
public class NotLoginException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "未登录！";
    private String message;

    public NotLoginException() {
        super();
    }

    public NotLoginException(String message) {
        super();
        this.message = message;
    }
    public NotLoginException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return message == null ? DEFAULT_MESSAGE : message;
    }
}
