package com.loneliess.controller.command_impl;

import com.loneliess.servise.ConeService;
import com.loneliess.servise.ServiceFactory;

public interface ConeCommand {
    ConeService service= ServiceFactory.getInstance().getConeService();
}
