package com.loneliess.repository;

public class RepositoryException extends Throwable{
    private String exceptionMessage;

    public RepositoryException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;

    }

    public RepositoryException(Throwable cause, String exceptionMessage) {
        super(cause);
        this.exceptionMessage = exceptionMessage;

    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
