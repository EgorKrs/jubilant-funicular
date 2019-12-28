package dao;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.NewsDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.News;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

public class NewDAOTest {
    private static Faker faker;
    private static NewsDAO dao;

    @BeforeClass
    public static void initialize() throws DAOException {
        faker=new Faker(new Locale("ru"));
        dao=new NewsDAO();
        dao.create(createValidMessage());
    }

    private static News createValidMessage()  {
        return  new News.Builder().setLast_update(LocalDate.now()).setText(faker.hipster().word()).build();
    }

    @Test
    public void createTest()throws  DAOException{
        News news=createValidMessage();
        News.Builder builder=new News.Builder(news);
        builder.setId(dao.create(news));
        news=builder.build();
        Assert.assertEquals(news,dao.receive(news));
    }
    @Test
    public void updateTest()throws  DAOException{
        News news=dao.receiveAll(new int[]{0,1}).iterator().next();
        News.Builder builder=new News.Builder(news);
        builder.setText(faker.crypto().md5());
        News changedNews=builder.build();
        dao.update(changedNews);
        Assert.assertNotEquals(news,dao.receive(changedNews));
    }
    @Test
    public void deleteTest()throws  DAOException{
        News news=dao.receiveAll(new int[]{0,1}).iterator().next();
        dao.delete(news);
        Assert.assertNotEquals(news,dao.receive(news));
    }
    @Test
    public void receiveTest()throws  DAOException{
        Collection<News> left=dao.receiveAll();
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll(new int[]{0,left.size()}).toArray());
    }
}
