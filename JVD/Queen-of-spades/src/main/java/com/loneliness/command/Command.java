package com.loneliness.command;

public interface Command<E,R ,T > {
    R execute(T note) throws CommandException;

    E undo() throws CommandException;
}
