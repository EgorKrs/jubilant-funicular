package com.loneliess.servise;

public class ServiceException extends Throwable{
    private String exceptionMessage;

    public ServiceException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;

    }

    public ServiceException(Throwable cause, String exceptionMessage) {
        super(cause);
        this.exceptionMessage = exceptionMessage;

    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
