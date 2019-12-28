package dao;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.MessageDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.Message;
import com.loneliness.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class MessageDAOTest {
    private static Faker faker;
    private static MessageDAO dao;


    @BeforeClass
    public static void initialize() throws DAOException {
        faker=new Faker(new Locale("ru"));
        dao=new MessageDAO();
        dao.create(createValidMessage());
    }

    private static Collection<User> getUsers() throws  DAOException {
        UserDAO userDAO=new UserDAO();
        return userDAO.receiveAll(new int[]{0,2});
    }
    private static Message createValidMessage() throws  DAOException{
        Message.Builder builder = new Message.Builder();
        Iterator<User> iterator= getUsers().iterator();
        builder.setToUser(iterator.next().getId())
                .setDate(LocalDate.now())
                .setFromUser(iterator.next().getId())
                .setMessage(faker.hipster().word());
        return builder.build();
    }
    @Test
    public void createTest() throws  DAOException {
        Message message=createValidMessage();
        Message.Builder builder=new Message.Builder(message);
        builder.setId(dao.create(message));
        message=builder.build();
        Assert.assertEquals(message,dao.receive(message));
    }
    @Test
    public void updateTest()throws  DAOException{
        Message message=dao.receiveAll(new int[]{0,1}).iterator().next();
        Message.Builder builder=new Message.Builder(message);
        builder.setMessage(faker.crypto().md5());
        Message changedMessage=builder.build();
        dao.update(changedMessage);
        Assert.assertNotEquals(message,dao.receive(changedMessage));
    }
    @Test
    public void deleteTest()throws  DAOException{
        Message message=dao.receiveAll(new int[]{0,1}).iterator().next();
        dao.delete(message);
        Assert.assertNotEquals(message,dao.receive(message));
    }
    @Test
    public void receiveTest()throws  DAOException{
        Collection<Message> left=dao.receiveAll();
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll(new int[]{0,left.size()}).toArray());
    }
}
