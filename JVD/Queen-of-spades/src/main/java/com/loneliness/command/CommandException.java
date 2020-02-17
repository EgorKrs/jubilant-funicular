package com.loneliness.command;
/**
 * класс исключений на слое command
 * @author Egor Krasouski
 *
 */
public class CommandException extends Throwable {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
