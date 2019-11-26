package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeWrapper;
import com.loneliess.servise.ServiceException;
import com.loneliess.servise.ServiceFactory;

public class UpdateCone implements Command<ConeWrapper,Boolean> {
    @Override
    public Boolean execute(ConeWrapper request) throws ControllerException {
        try {
            return ServiceFactory.getInstance().getConeLogic().updateCone(request);
        } catch (ServiceException e) {
            throw new ControllerException(e,"Ошибка команды обновления данных "+e.getExceptionMessage());
        }
    }
}
