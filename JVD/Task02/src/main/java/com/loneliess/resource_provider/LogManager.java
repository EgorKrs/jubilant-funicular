package com.loneliess.resource_provider;

import com.loneliess.servise.ConeLogic;
import org.apache.logging.log4j.Logger;

public class LogManager {
    private static final LogManager instance =new LogManager();
    private final Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private LogManager(){

    }

    public static LogManager getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
