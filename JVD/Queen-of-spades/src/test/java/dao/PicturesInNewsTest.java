package dao;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.NewsDAO;
import com.loneliness.dao.sql_dao_impl.PictureDAO;
import com.loneliness.dao.sql_dao_impl.PicturesInNewsDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.News;
import com.loneliness.entity.Picture;
import com.loneliness.entity.PicturesInNews;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Locale;

public class PicturesInNewsTest {
    private static NewsDAO newsDAO;
    private static PictureDAO pictureDAO;
    private static PicturesInNewsDAO dao;



    @BeforeClass
    public static void initialize() throws DAOException {
        newsDAO=new NewsDAO();
        pictureDAO=new PictureDAO();
        dao=new PicturesInNewsDAO();
        dao.create(createValidPicturesInNews());
    }

    private static PicturesInNews createValidPicturesInNews() throws  DAOException{
        News news = newsDAO.receiveAll(new int[]{0, 1}).iterator().next();
        Picture picture = pictureDAO.receiveAll(new int[]{0, 1}).iterator().next();
        return new PicturesInNews.Builder().setPictureID(picture.getId()).setNewsID(news.getId()).build();
    }

    @Test
    public void createTest() throws  DAOException{
        PicturesInNews picturesInNews=createValidPicturesInNews();
        PicturesInNews.Builder builder=new PicturesInNews.Builder(picturesInNews);
        builder.setId(dao.create(picturesInNews));
        picturesInNews=builder.build();
        Assert.assertEquals(picturesInNews,dao.receive(picturesInNews));
    }

    @Test
    public void deleteTest()throws  DAOException{
        PicturesInNews picturesInNews=dao.receiveAll(new int[]{0,1}).iterator().next();
        dao.delete(picturesInNews);
        Assert.assertNotEquals(picturesInNews,dao.receive(picturesInNews));
    }
    @Test
    public void receiveTest()throws  DAOException{
        Collection<PicturesInNews> left=dao.receiveAll();
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll(new int[]{0,left.size()}).toArray());
    }
}
