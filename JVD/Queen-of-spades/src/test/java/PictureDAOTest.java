import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.PictureDAO;
import com.loneliness.entity.Picture;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class PictureDAOTest {
    private static Faker faker;
    private PictureDAO dao=new PictureDAO();

    public PictureDAOTest() throws DAOException {
    }

    @BeforeClass
    public static void initialize()  {
        faker=new Faker(new Locale("ru"));
    }

    private Picture createValidPicture() throws IOException {
        Picture.Builder builder = new Picture.Builder();
        builder.setName("Anonymous.png")
                .setLastUpdate(LocalDate.now());

        FileInputStream stream= new FileInputStream(new File("data\\"+builder.getName()));
        builder.setByteImage(stream.readAllBytes());
        return builder.build();
    }
    @Test
    public void createTest() throws IOException, DAOException {
        Picture picture=createValidPicture();
        Picture.Builder builder=new Picture.Builder(picture);
        builder.setId(dao.create(picture));
        picture=builder.build();
        Assert.assertEquals(picture,dao.receive(picture));
    }
    @Test
    public void updateTest()throws  DAOException{
        Picture picture=dao.receiveAll(new int[]{1,2}).iterator().next();
        Picture.Builder builder=new Picture.Builder(picture);
        builder.setName(faker.funnyName().name());
        Picture changedMessage=builder.build();
        dao.update(changedMessage);
        Assert.assertNotEquals(picture,dao.receive(changedMessage));
        dao.update(picture);

    }
    @Test
    public void deleteTest()throws  DAOException{
        Picture picture=dao.receiveAll(new int[]{1,2}).iterator().next();
        dao.delete(picture);
        Assert.assertNotEquals(picture,dao.receive(picture));
    }
    @Test
    public void receiveTest()throws  DAOException{
        int lengthAll=dao.receiveAll().size();
        int lengthInLim=dao.receiveAll(new int[]{0,lengthAll}).size();
        Assert.assertEquals(lengthAll,lengthInLim);
    }

}
