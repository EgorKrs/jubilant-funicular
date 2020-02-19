package com.loneliness.command;
/**
 * общий интерфейс комманд
 *
 * @author Egor Krasouski
 * @version 1
 */
public interface Command<E, R, T> {
    /**
     * общий метод выполнения комманд
     *
     * @param note объект над которым будут выполняться или использоваться для выполнения запроса
     * @return подтверждение выполнения или результат выполнения
     * @throws CommandException если что то пошло не так
     */
    R execute(T note) throws CommandException;

    /**
     * общий метод откат результата комманд если это возможно
     *
     * @return данные до выполнения команды
     */
    E undo() throws CommandException;
}
