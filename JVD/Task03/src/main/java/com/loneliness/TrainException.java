package com.loneliness;

public class TrainException extends RuntimeException{
    public TrainException(String message) {
        super(message);
    }

    public TrainException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainException(Throwable cause) {
        super(cause);
    }

}
