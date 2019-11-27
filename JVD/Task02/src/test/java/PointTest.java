import com.loneliess.entity.Point;
import com.loneliess.servise.PointService;
import org.junit.Assert;
import org.junit.Test;

public class PointTest {
    @Test public void isLiesOnThePlaneT(){
        Point point=new Point(1,2,3);
        Assert.assertFalse(PointService.getInstance().isLiesOnThePlane(point));
    }
    @Test public void isLiesOnThePlaneF(){
        Point point=new Point(1,0,3);
        Assert.assertTrue(PointService.getInstance().isLiesOnThePlane(point));
    }
}
