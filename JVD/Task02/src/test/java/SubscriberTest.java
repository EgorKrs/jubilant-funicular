
import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeWrapper;
import org.junit.Assert;
import org.junit.Test;

public class SubscriberTest {
    @Test
    public void updateTest() {
        ConeWrapper cone = new ConeWrapper();
        double q=cone.getSubscriber().getSideSurfaceArea();
        double w=cone.getSubscriber().getSurfaceArea();
        double c=cone.getSubscriber().getVolume();
        cone.updateCone(new Cone(9999, 2, 5, 3, 4, 5, 4, 2, 2, 5));
        try {
            Thread.sleep(1000); //поток доллжен успеть обработать изменения перед тем как проверять измененные значения
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(q, cone.getSubscriber().getSideSurfaceArea());
        Assert.assertNotEquals(w, cone.getSubscriber().getSurfaceArea());
        Assert.assertNotEquals(c, cone.getSubscriber().getVolume());
    }


}
