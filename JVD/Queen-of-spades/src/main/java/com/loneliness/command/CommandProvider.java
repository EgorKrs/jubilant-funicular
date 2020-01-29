package com.loneliness.command;

public enum CommandProvider {
    AUTHORIZATION;

    private Command command;
    public Command getCommand(){
        return command;
    }
    public void setCommand(Command command){
        this.command=command;
    }
}
