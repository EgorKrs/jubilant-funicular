package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.MessageDAO;
import com.loneliness.dao.sql_dao_impl.NewsDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Message;
import com.loneliness.entity.News;
import com.loneliness.entity.User;
import com.loneliness.service.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.service.common_service.Create;
import com.loneliness.service.common_service.Delete;
import com.loneliness.service.common_service.ReceiveAll;
import com.loneliness.service.common_service.Update;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class NewServiceTest {
    private static News valid;
    private static Faker faker;
    private static NewsDAO dao;
    private static Command<Integer,Integer, News> commonCommand;

    @BeforeClass
    public static void initialize() throws DAOException, ServiceException {
        dao=new NewsDAO();
        faker=new Faker(new Locale("ru"));
        commonCommand =new Create<>(new <News>NewsDAO());
        News.Builder builder=new News.Builder(createValidMessage());
        builder.setId(commonCommand.execute(builder.build()));
        valid =builder.build();


    }

    @Test
    public void createTest() throws DAOException, ServiceException {
        News data= createValidMessage();
        News created=dao.receive(commonCommand.execute(data));
        News.Builder builder=new News.Builder(data);
        builder.setId(created.getId());
        data=builder.build();
        Assert.assertEquals(data,created);
        created=dao.receive(commonCommand.undo());
        Assert.assertNotEquals(data,created);
    }
    @Test
    public void updateTest() throws DAOException, ServiceException {

        News unChanged=dao.receive(valid);
        commonCommand =new Update<>(new <News>NewsDAO()).setData(unChanged);
        News.Builder builder=new News.Builder(unChanged);
        builder.setText(faker.crypto().md5());
        News changed=builder.build();
        commonCommand.execute(changed);
        Assert.assertNotEquals(unChanged,dao.receive(changed));
        commonCommand.undo();
        Assert.assertEquals(unChanged,dao.receive(changed));
    }
    @Test
    public void deleteTest() throws DAOException, ServiceException {
        News data=dao.receive(valid);
        boolean similar=false;
        commonCommand =new Delete<>(new <News>NewsDAO());
        commonCommand.execute(data);
        Assert.assertNotEquals(data,dao.receive(data));

        News deleted=dao.receive(commonCommand.undo());
        if(deleted.getText().equals(data.getText()))
            similar=true;
        Assert.assertTrue(similar);
    }
    @Test
    public void receiveTest() throws DAOException, ServiceException {
        Command<Collection<News>, Collection<News>,News> commonAccountCommand =new ReceiveAll<>(new <News>NewsDAO());
        Collection<News> left=commonAccountCommand.execute( new News.Builder().build());
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll().toArray());
    }
    @AfterClass
    public static void clean() throws DAOException {
        dao.delete(valid);
    }



    private static News createValidMessage()  {
        return  new News.Builder().setLast_update(LocalDate.now()).setText(faker.hipster().word()).build();
    }
}
