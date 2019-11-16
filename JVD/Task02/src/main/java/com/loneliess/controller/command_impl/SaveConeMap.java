package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.servise.ServiceException;
import com.loneliess.servise.ServiceFactory;

public class SaveConeMap implements Command<Object,Boolean> {
    @Override
    public Boolean execute(Object request) throws ControllerException {
        try {
            return ServiceFactory.getInstance().getConeLogic().saveConeMap();
        } catch (ServiceException e) {
            throw new ControllerException(e,"Ошибка команды загрузки данных "+e.getExceptionMessage());
        }
    }
}
