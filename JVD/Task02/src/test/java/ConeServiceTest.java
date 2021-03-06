import com.loneliess.controller.CommandName;
import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.servise.ServiceFactory;
import org.junit.Assert;
import org.junit.Test;


public class ConeServiceTest {
    private Cone cone = new Cone(99, 2, 3, 4, 0, -1, 0, 0, 2, 0);
    private CommandProvider commandProvider=CommandProvider.getCommandProvider();
    @Test
    public void calculateSideSurfaceAreaTest() throws ControllerException {

        Assert.assertEquals((Double) commandProvider.getCommand(CommandName.CALCULATE_VOLUME_OF_CONE).execute(cone), 37.68, 0.1);

    }

    @Test
    public void CalculateSideSurfaceAreaOfConeTest() throws ControllerException {

        Assert.assertEquals((Double) commandProvider.getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA_OF_CONE).execute(cone), 47.1, 0.1);

    }

    @Test
    public void CalculateSideSurfaceArea() throws ControllerException {

        Assert.assertEquals((Double) commandProvider.getCommand(CommandName.CALCULATE_SIDE_SURFACE_AREA).execute(cone), 18.84, 0.1);
    }

    @Test
    public void CalculateVolumeRatio() throws ControllerException {
        Assert.assertEquals((Double) commandProvider.getCommand(CommandName.CALCULATE_VOLUME_RATIO).execute(cone), 339.29, 0.1);
    }

    @Test
    public void isLiesOnThePlaneCone() throws ControllerException {
        Assert.assertEquals((Boolean) commandProvider.getCommand(CommandName.IS_LIES_ON_THE_PLANE_CONE).execute(cone), true);
    }

    @Test
    public void isEquals() throws ControllerException {
        Cone newCone = new Cone(94, 2, 3, 4, 3, -1, 0, 2, 2, 0);
        Assert.assertFalse(ServiceFactory.getInstance().getConeService().isEquals(cone, newCone));
    }


}
