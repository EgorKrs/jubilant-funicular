package com.loneliness.command;
/**
 * общий интерфейс комманд
 * @author Egor Krasouski
 * @version 1
 *
 */
public interface Command<E,R ,T > {
    R execute(T note) throws CommandException;

    E undo() throws CommandException;
}
