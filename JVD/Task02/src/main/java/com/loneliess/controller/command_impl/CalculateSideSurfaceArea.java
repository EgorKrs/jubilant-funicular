package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.servise.ServiceFactory;

public class CalculateSideSurfaceArea implements Command<Cone,Double> {
    @Override
    public Double execute(Cone request) throws ControllerException {
        return ServiceFactory.getInstance().getConeLogic().calculateSideSurfaceArea(request);
    }
}

