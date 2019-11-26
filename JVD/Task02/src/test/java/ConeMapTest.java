import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeWrapper;
import com.loneliess.repository.RepositoryFactory;
import com.loneliess.servise.ServiceFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ConeMapTest {
    @Test
    public void loadTest() throws ControllerException {
        Assert.assertNotEquals(CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object()), 0);
    }

    @Test
    public void addValidConeTest() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = RepositoryFactory.getInstance().getRepositoryCone().getData().size();
        CommandProvider.getCommandProvider().getCommand(CommandName.ADD_CONE).execute(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Assert.assertEquals(RepositoryFactory.getInstance().getRepositoryCone().getData().size(), size + 1);
    }

    @Test
    public void addInvalidConeTest() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = RepositoryFactory.getInstance().getRepositoryCone().getData().size();
        CommandProvider.getCommandProvider().getCommand(CommandName.ADD_CONE).execute(new double[]{0, 0, 0, 4, 5, 6, 7, 8, 9});
        Assert.assertNotEquals(RepositoryFactory.getInstance().getRepositoryCone().getData().size(), size + 1);
    }

    @Test
    public void saveConeMapTest() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        HashMap<Integer, Cone> before = RepositoryFactory.getInstance().getRepositoryCone().getData();
        CommandProvider.getCommandProvider().getCommand(CommandName.SAVE_CONE_MAP).execute(new Object());
        RepositoryFactory.getInstance().getRepositoryCone().getData().clear();
        CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Assert.assertEquals(before, RepositoryFactory.getInstance().getRepositoryCone().getData());
    }

    @Test
    public void deleteConeTest() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        ConeWrapper cone=new ConeWrapper(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        ServiceFactory.getInstance().getConeLogic().addCone(cone);
        CommandProvider.getCommandProvider().getCommand(CommandName.DELETE_CONE).execute(cone.getCone());
        Assert.assertFalse(RepositoryFactory.getInstance().getRepositoryCone().getData().containsKey(9999));
    }
    @Test public void updateCone() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        ConeWrapper oldCone=new ConeWrapper(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        ServiceFactory.getInstance().getConeLogic().addCone(oldCone);
        ConeWrapper newCone=new ConeWrapper(9999,3, 5,   2, 5, 5, 4, 2, 2, 5);
        CommandProvider.getCommandProvider().getCommand(CommandName.UPDATE_CONE).execute(newCone);
        Assert.assertNotEquals(RepositoryFactory.getInstance().getRepositoryCone().getData().get(9999),oldCone);
    }

}
