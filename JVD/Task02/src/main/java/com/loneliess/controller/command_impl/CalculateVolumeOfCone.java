package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.servise.ServiceFactory;

public class CalculateVolumeOfCone implements Command<Cone,Double>,ConeCommand {
    @Override
    public Double execute(Cone request) throws ControllerException {
        return service.calculateVolume(request);
    }
}
