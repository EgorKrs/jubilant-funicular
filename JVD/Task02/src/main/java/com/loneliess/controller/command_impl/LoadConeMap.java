package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.repository.RepositoryCone;
import com.loneliess.repository.RepositoryException;
import com.loneliess.repository.RepositoryFactory;

public class LoadConeMap implements Command<Object,Integer> {
    private RepositoryCone repository=RepositoryFactory.getInstance().getRepositoryCone();
    @Override
    public Integer execute(Object request) throws ControllerException {
        try {
            repository.loadAllCones();
            return repository.getData().size();
        } catch (RepositoryException e) {
       throw new ControllerException(e,"Ошибка команды загрузки данных "+e.getExceptionMessage());
    }
    }
}
