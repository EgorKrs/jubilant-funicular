package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.PictureDAO;
import com.loneliness.entity.Picture;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.command.common_comand.Create;
import com.loneliness.command.common_comand.Delete;
import com.loneliness.command.common_comand.ReceiveAll;
import com.loneliness.command.common_comand.Update;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

public class PictureServiceTest {
    private static Picture valid;
    private static Faker faker;
    private static PictureDAO dao;
    private static Command<Integer,Integer, Picture> commonCommand;

    @BeforeClass
    public static void initialize() throws DAOException, ServiceException, IOException {
        dao=new PictureDAO();
        faker=new Faker(new Locale("ru"));
        commonCommand =new Create<>(new <Picture>PictureDAO());
        Picture.Builder builder=new Picture.Builder(createValid());
        builder.setId(commonCommand.execute(builder.build()));
        valid =builder.build();


    }

    @Test
    public void createTest() throws DAOException, ServiceException, IOException {
        Picture data= createValid();
        Picture created=dao.receive(commonCommand.execute(data));
        Picture.Builder builder=new Picture.Builder(data);
        builder.setId(created.getId());
        data=builder.build();
        Assert.assertEquals(data,created);
        created=dao.receive(commonCommand.undo());
        Assert.assertNotEquals(data,created);
    }
    @Test
    public void updateTest() throws DAOException, ServiceException {

        Picture unChanged=dao.receive(valid);
        commonCommand =new Update<>(new <Picture>PictureDAO()).setData(unChanged);
        Picture.Builder builder=new Picture.Builder(unChanged);
        builder.setName(faker.funnyName().name());
        Picture changed=builder.build();
        commonCommand.execute(changed);
        Assert.assertNotEquals(unChanged,dao.receive(changed));
        commonCommand.undo();
        Assert.assertEquals(unChanged,dao.receive(changed));
    }
    @Test
    public void deleteTest() throws DAOException, ServiceException {
        Picture data=dao.receive(valid);
        boolean similar=false;
        commonCommand =new Delete<>(new <Picture>PictureDAO());
        commonCommand.execute(data);
        Assert.assertNotEquals(data,dao.receive(data));

        Picture deleted=dao.receive(commonCommand.undo());
        if( Arrays.equals(deleted.getByteImage(), data.getByteImage()) &&
                deleted.getName().equals(data.getName()))
            similar=true;
        Assert.assertTrue(similar);
    }
    @Test
    public void receiveTest() throws DAOException, ServiceException {
        Command<Collection<Picture>, Collection<Picture>,Picture> commonAccountCommand =new ReceiveAll<>(new <Picture>PictureDAO());
        Collection<Picture> left=commonAccountCommand.execute( new Picture.Builder().build());
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll().toArray());
    }
    @AfterClass
    public static void clean() throws DAOException {
        dao.delete(valid);
    }



    private static Picture createValid() throws IOException {
        Picture.Builder builder = new Picture.Builder();
        builder.setName("Anonymous.png")
                .setLastUpdate(LocalDate.now());

        FileInputStream stream= new FileInputStream(new File("data\\"+builder.getName()));
        builder.setByteImage(stream.readAllBytes());
        return builder.build();
    }
}
