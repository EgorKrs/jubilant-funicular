import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.repository.RepositoryCone;
import com.loneliess.servise.ConeService;
import com.loneliess.servise.ConeWrapper;
import com.loneliess.repository.RepositoryFactory;
import com.loneliess.servise.ServiceFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ConeMapTest {
    private CommandProvider commandProvider=CommandProvider.getCommandProvider();
    private RepositoryCone repository=RepositoryFactory.getInstance().getRepositoryCone();
    private ConeService service= ServiceFactory.getInstance().getConeService();

    @Test
    public void loadTest() throws ControllerException {
        Assert.assertNotEquals(commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object()), 0);
    }

    @Test
    public void addValidConeTest() throws ControllerException {
        if (repository.getData().size() == 0)
            commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = repository.getData().size();
        commandProvider.getCommand(CommandName.ADD_CONE).execute(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Assert.assertEquals(repository.getData().size(), size + 1);
    }

    @Test
    public void addInvalidConeTest() throws ControllerException {
        if (repository.getData().size() == 0)
            commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = repository.getData().size();
        commandProvider.getCommand(CommandName.ADD_CONE).execute(new double[]{0, 0, 0, 4, 5, 6, 7, 8, 9});
        Assert.assertNotEquals(repository.getData().size(), size + 1);
    }

    @Test
    public void saveConeMapTest() throws ControllerException {
        if (repository.getData().size() == 0)
            commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Collection<Cone> before=repository.getData();
        commandProvider.getCommand(CommandName.SAVE_CONE_MAP).execute(new Object());
        RepositoryFactory.getInstance().getRepositoryCone().getData().clear();
        commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Assert.assertEquals(before, repository.getData());
    }

    @Test
    public void deleteConeTest() throws ControllerException {
        if (repository.getData().size() == 0)
            commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        ConeWrapper cone=new ConeWrapper(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        service.addCone(cone);
        commandProvider.getCommand(CommandName.DELETE_CONE).execute(cone.getCone());
        Assert.assertFalse(repository.getData().contains(cone.getCone()));
    }
    @Test public void updateCone() throws ControllerException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().size() == 0)
            commandProvider.getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        ConeWrapper oldCone=new ConeWrapper(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        service.addCone(oldCone);
        ConeWrapper newCone=new ConeWrapper(9999,3, 5,   2, 5, 5, 4, 2, 2, 5);
        commandProvider.getCommand(CommandName.UPDATE_CONE).execute(newCone);
        Assert.assertNotEquals(repository.getData().contains(newCone),oldCone);
    }

}
