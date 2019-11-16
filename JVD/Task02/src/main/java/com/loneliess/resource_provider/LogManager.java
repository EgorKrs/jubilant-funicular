package com.loneliess.resource_provider;

import org.apache.logging.log4j.Logger;

public class LogManager {
    private static final LogManager instance =new LogManager();
    private final Logger coneLogger = org.apache.logging.log4j.LogManager.getLogger("simpleLogs");
    private LogManager(){

    }

    public static LogManager getInstance() {
        return instance;
    }

    public Logger getConeLogger() {
        return coneLogger;
    }
}
