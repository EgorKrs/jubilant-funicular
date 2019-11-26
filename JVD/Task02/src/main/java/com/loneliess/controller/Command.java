package com.loneliess.controller;

public interface Command <T, R> {
    R execute(T request) throws ControllerException;
}
