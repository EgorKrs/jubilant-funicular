import com.loneliness.Train;
import com.loneliness.entity.Direction;
import com.loneliness.service.Dispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DispatcherTest {
    private static Logger logger= LogManager.getLogger();
    private static Random random;
    private Dispatcher dispatcher=Dispatcher.getInstance();
    @BeforeClass
    public static void prepareToTest(){
       random=new Random();
    }

    private Train createTrain(){
        return new Train(random.nextInt(100), Direction.values()[random.nextInt(2)]);
    }
    @Test
    public void dispatcherSendTest() throws InterruptedException {
        int quantity=30;
        for (int i = 0; i < quantity; i++) {
            dispatcher.sendToQueue(createTrain());
        }
        logger.info("\n\n");
        dispatcher.sendToTunnel();
        TimeUnit.SECONDS.sleep(25);
        Assert.assertEquals(quantity,Dispatcher.successCase.get());
    }
}
