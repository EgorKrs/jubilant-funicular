package com.loneliess;

import com.loneliess.controller.Command;
import com.loneliess.controller.CommandName;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.loneliess.controller.CommandProvider;
import com.loneliess.repository.RepositoryFactory;

public class UniqueID {
    private static final UniqueID instance=new UniqueID();
    private AtomicInteger id = new AtomicInteger();

    public static UniqueID getInstance() {
        return instance;
    }

    public int getId() {
        try {
            if (id.get() == 0) {
                if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0) {
                    id.set((Integer) CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object()));
                } else {
                    id.set((Integer) (RepositoryFactory.getInstance().getRepositoryCone().getData().keySet().toArray())[RepositoryFactory.getInstance().getRepositoryCone().getData().keySet().toArray().length - 1]);
                }
            }
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        return id.addAndGet(1);
    }
}
