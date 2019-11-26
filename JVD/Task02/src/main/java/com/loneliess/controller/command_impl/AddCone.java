package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.servise.ServiceFactory;

public class AddCone implements Command<double[],Boolean> {
    @Override
    public Boolean execute(double[] request) throws ControllerException {
        return ServiceFactory.getInstance().getConeLogic().addCone(request[0],request[1],request[2],request[3],
                request[4],request[5],request[6],request[7],request[8]);
    }
}
