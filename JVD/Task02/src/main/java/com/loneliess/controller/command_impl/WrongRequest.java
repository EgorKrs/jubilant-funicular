package com.loneliess.controller.command_impl;


import com.loneliess.controller.Command;

public class WrongRequest implements Command<Object,String> {
    @Override
    public String execute(Object request) {
        return "Неверный запрос";
    }
}
