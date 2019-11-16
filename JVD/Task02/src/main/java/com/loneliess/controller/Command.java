package com.loneliess.controller;

public interface Command <Type,ReturnType> {
    ReturnType execute(Type request) throws ControllerException;
}
