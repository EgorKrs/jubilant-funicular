import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeMap;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ConeMapTest {
    @Test public void loadTest() throws ControllerException {
            Assert.assertNotEquals( CommandProvider.getCommandProvider().getCommand("LOAD_CONE_MAP").execute(new Object()),0);
    }
    @Test public void addValidConeTest() throws ControllerException {
        if(ConeMap.getInstance().getData().size()==0)
        CommandProvider.getCommandProvider().getCommand("LOAD_CONE_MAP").execute(new Object());
        int size=ConeMap.getInstance().getData().size();
        CommandProvider.getCommandProvider().getCommand("ADD_CONE").execute(new double[]{1,2,3,4,5,6,7,8,9});
        Assert.assertEquals( ConeMap.getInstance().getData().size(),size+1);
    }
    @Test public void addInvalidConeTest() throws ControllerException {
        if(ConeMap.getInstance().getData().size()==0)
        CommandProvider.getCommandProvider().getCommand("LOAD_CONE_MAP").execute(new Object());
        int size=ConeMap.getInstance().getData().size();
        CommandProvider.getCommandProvider().getCommand("ADD_CONE").execute(new double[]{0,0,0,4,5,6,7,8,9});
        Assert.assertNotEquals( ConeMap.getInstance().getData().size(),size+1);
    }
    @Test public void SaveConeMapTest() throws ControllerException {
        if(ConeMap.getInstance().getData().size()==0)
            CommandProvider.getCommandProvider().getCommand("LOAD_CONE_MAP").execute(new Object());
        HashMap<Integer, Cone> before=ConeMap.getInstance().getData();
        CommandProvider.getCommandProvider().getCommand("SAVE_CONE_MAP").execute(new Object());
        ConeMap.getInstance().getData().clear();
        CommandProvider.getCommandProvider().getCommand("LOAD_CONE_MAP").execute(new Object());
        Assert.assertEquals(before,ConeMap.getInstance().getData());
    }
}
