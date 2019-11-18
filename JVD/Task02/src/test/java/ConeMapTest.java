import com.loneliess.UniqueID;
import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeMap;
import com.loneliess.servise.ServiceFactory;
import org.hamcrest.MatcherAssert;
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
        if (ConeMap.getInstance().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = ConeMap.getInstance().getData().size();
        CommandProvider.getCommandProvider().getCommand(CommandName.ADD_CONE).execute(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Assert.assertEquals(ConeMap.getInstance().getData().size(), size + 1);
    }

    @Test
    public void addInvalidConeTest() throws ControllerException {
        if (ConeMap.getInstance().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        int size = ConeMap.getInstance().getData().size();
        CommandProvider.getCommandProvider().getCommand(CommandName.ADD_CONE).execute(new double[]{0, 0, 0, 4, 5, 6, 7, 8, 9});
        Assert.assertNotEquals(ConeMap.getInstance().getData().size(), size + 1);
    }

    @Test
    public void saveConeMapTest() throws ControllerException {
        if (ConeMap.getInstance().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        HashMap<Integer, Cone> before = ConeMap.getInstance().getData();
        CommandProvider.getCommandProvider().getCommand(CommandName.SAVE_CONE_MAP).execute(new Object());
        ConeMap.getInstance().getData().clear();
        CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Assert.assertEquals(before, ConeMap.getInstance().getData());
    }

    @Test
    public void deleteConeTest() throws ControllerException {
        if (ConeMap.getInstance().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Cone cone=new Cone(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        ServiceFactory.getInstance().getConeLogic().addCone(cone);
        CommandProvider.getCommandProvider().getCommand(CommandName.DELETE_CONE).execute(cone);
        Assert.assertFalse(ConeMap.getInstance().getData().containsKey(9999));
    }
    @Test public void updateCone() throws ControllerException {
        if (ConeMap.getInstance().getData().size() == 0)
            CommandProvider.getCommandProvider().getCommand(CommandName.LOAD_CONE_MAP).execute(new Object());
        Cone oldCone=new Cone(9999,5, 5,   5, 5, 5, 5, 2, 2, 2);
        ServiceFactory.getInstance().getConeLogic().addCone(oldCone);
        Cone newCone=new Cone(9999,3, 5,   2, 5, 5, 4, 2, 2, 5);
        CommandProvider.getCommandProvider().getCommand(CommandName.UPDATE_CONE).execute(newCone);
        Assert.assertNotEquals(ConeMap.getInstance().getData().get(9999),oldCone);
    }
}
