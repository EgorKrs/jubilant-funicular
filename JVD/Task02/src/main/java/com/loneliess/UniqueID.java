package com.loneliess;

import com.loneliess.controller.Command;
import com.loneliess.controller.CommandName;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.loneliess.controller.CommandProvider;
import com.loneliess.repository.RepositoryCone;
import com.loneliess.repository.RepositoryFactory;

public class UniqueID {
    private static final UniqueID instance=new UniqueID();
    private AtomicInteger id = new AtomicInteger();
    private CommandProvider provider;
    private RepositoryCone repository=RepositoryFactory.getInstance().getRepositoryCone();

    public static UniqueID getInstance() {
        return instance;
    }

    public int getId() {
        try {
            if (id.get() == 0) {
                if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0) {
                    id.set((Integer) provider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object()));
                } else {
                    id.set( (repository.getData().size()-1));
                }
            }
        } catch (ControllerException e) {
            e.printStackTrace();
        }
        return id.addAndGet(1);
    }
}
