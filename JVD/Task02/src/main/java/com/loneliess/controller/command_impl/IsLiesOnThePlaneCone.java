package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.servise.ServiceFactory;

public class IsLiesOnThePlaneCone implements Command<Cone,Boolean> {
    @Override
    public Boolean execute(Cone request) throws ControllerException {
        return ServiceFactory.getInstance().getConeLogic().isLiesOnThePlane(request);
    }
}
