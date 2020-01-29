package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.NewsDAO;
import com.loneliness.dao.sql_dao_impl.PictureDAO;
import com.loneliness.dao.sql_dao_impl.PicturesInNewsDAO;
import com.loneliness.entity.News;
import com.loneliness.entity.Picture;
import com.loneliness.entity.PicturesInNews;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.command.common_comand.Create;
import com.loneliness.command.common_comand.Delete;
import com.loneliness.command.common_comand.ReceiveAll;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

public class PicturesInNewsServiceTest {
    private static PicturesInNews valid;
    private static Faker faker;
    private static PicturesInNewsDAO dao;
    private static NewsDAO newsDAO;
    private static PictureDAO pictureDAO;
    private static Command<Integer,Integer, PicturesInNews> commonCommand;

    @BeforeClass
    public static void initialize() throws DAOException, ServiceException, IOException {
        dao=new PicturesInNewsDAO();
        faker=new Faker(new Locale("ru"));
        newsDAO=new NewsDAO();
        pictureDAO=new PictureDAO();
        commonCommand =new Create<>(new <PicturesInNews>PicturesInNewsDAO());
        PicturesInNews.Builder builder=new PicturesInNews.Builder(createValid());
        builder.setId(commonCommand.execute(builder.build()));
        valid =builder.build();


    }

    @Test
    public void createTest() throws DAOException, ServiceException, IOException {
        PicturesInNews data= createValid();
        PicturesInNews created=dao.receive(commonCommand.execute(data));
        PicturesInNews.Builder builder=new PicturesInNews.Builder(data);
        builder.setId(created.getId());
        data=builder.build();
        Assert.assertEquals(data,created);
        created=dao.receive(commonCommand.undo());
        Assert.assertNotEquals(data,created);
    }

    @Test
    public void deleteTest() throws DAOException, ServiceException {
        PicturesInNews data=dao.receive(valid);
        boolean similar=false;
        commonCommand =new Delete<>(new <PicturesInNews>PicturesInNewsDAO());
        commonCommand.execute(data);
        Assert.assertNotEquals(data,dao.receive(data));

        PicturesInNews deleted=dao.receive(commonCommand.undo());
        if(  deleted.getNewsID() == data.getNewsID() &&
                deleted.getPictureID() == data.getPictureID())
            similar=true;
        Assert.assertTrue(similar);
    }
    @Test
    public void receiveTest() throws DAOException, ServiceException {
        Command<Collection<PicturesInNews>, Collection<PicturesInNews>,PicturesInNews> commonAccountCommand =
                new ReceiveAll<>(new <PicturesInNews>PicturesInNewsDAO());
        Collection<PicturesInNews> left=commonAccountCommand.execute( new PicturesInNews.Builder().build());
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll().toArray());
    }
    @AfterClass
    public static void clean() throws DAOException {
        dao.delete(valid);
    }



    private static PicturesInNews createValid() throws DAOException {
        News news = newsDAO.receiveAll(new int[]{0, 1}).iterator().next();
        Picture picture = pictureDAO.receiveAll(new int[]{0, 1}).iterator().next();
        return new PicturesInNews.Builder().setPictureID(picture.getId()).setNewsID(news.getId()).build();

    }
}
