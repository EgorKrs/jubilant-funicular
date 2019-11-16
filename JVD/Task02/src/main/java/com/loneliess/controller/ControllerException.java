package com.loneliess.controller;

public class ControllerException extends Throwable{
    private String exceptionMessage;

    public ControllerException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;

    }

    public ControllerException(Throwable cause, String exceptionMessage) {
        super(cause);
        this.exceptionMessage = exceptionMessage;

    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
