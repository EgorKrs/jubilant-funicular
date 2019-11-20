package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.repository.RepositoryException;
import com.loneliess.repository.RepositoryFactory;

public class LoadConeMap implements Command<Object,Integer> {
    @Override
    public Integer execute(Object request) throws ControllerException {
        try {
            RepositoryFactory.getInstance().getRepositoryCone().setData(RepositoryFactory.getInstance().getRepositoryCone().getMap());

        return (Integer) (RepositoryFactory.getInstance().getRepositoryCone().getData().keySet().toArray())[RepositoryFactory.getInstance().getRepositoryCone().getData().keySet().toArray().length -1];
        } catch (RepositoryException e) {
       throw new ControllerException(e,"Ошибка команды загрузки данных "+e.getExceptionMessage());
    }
    }
}
