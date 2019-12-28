package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.AccountDAO;
import com.loneliness.dao.sql_dao_impl.MessageDAO;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.Account;
import com.loneliness.entity.Message;
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

public class MessageServiceTest {
    private static Message valid;
    private static Faker faker;
    private static MessageDAO dao;
    private static Command<Integer,Integer, Message> commonCommand;

    @BeforeClass
    public static void initialize() throws DAOException, ServiceException {
        dao=new MessageDAO();
        faker=new Faker(new Locale("ru"));
        commonCommand =new Create<>(new <Message>MessageDAO());
        Message.Builder builder=new Message.Builder(createValidMessage());
        builder.setId(commonCommand.execute(builder.build()));
        valid =builder.build();


    }

    @Test
    public void createTest() throws DAOException, ServiceException {
        Message data= createValidMessage();
        Message created=dao.receive(commonCommand.execute(data));
        Message.Builder builder=new Message.Builder(data);
        builder.setId(created.getId());
        data=builder.build();
        Assert.assertEquals(data,created);
        created=dao.receive(commonCommand.undo());
        Assert.assertNotEquals(data,created);
    }
    @Test
    public void updateTest() throws DAOException, ServiceException {

        Message unChanged=dao.receive(valid);
        commonCommand =new Update<>(new <Message>MessageDAO()).setData(unChanged);
        Message.Builder builder=new Message.Builder(unChanged);
        builder.setMessage(faker.crypto().md5());
        Message changed=builder.build();
        commonCommand.execute(changed);
        Assert.assertNotEquals(unChanged,dao.receive(changed));
        commonCommand.undo();
        Assert.assertEquals(unChanged,dao.receive(changed));
    }
    @Test
    public void deleteTest() throws DAOException, ServiceException {
        Message data=dao.receive(valid);
        boolean similar=false;
        commonCommand =new Delete<>(new <Message>MessageDAO());
        commonCommand.execute(data);
        Assert.assertNotEquals(data,dao.receive(data));

        Message deleted=dao.receive(commonCommand.undo());
        if(     deleted.getToUser() ==data.getToUser() &&
                deleted.getFromUser() == data.getFromUser() &&
                deleted.getMessage().equals(data.getMessage()) &&
                deleted.getDate().equals(data.getDate()))
            similar=true;
        Assert.assertTrue(similar);
    }
    @Test
    public void receiveTest() throws DAOException, ServiceException {
        Command<Collection<Message>, Collection<Message>,Message> commonAccountCommand =new ReceiveAll<>(new <Message>MessageDAO());
        Collection<Message> left=commonAccountCommand.execute( new Message.Builder().build());
        Assert.assertArrayEquals(left.toArray(),dao.receiveAll().toArray());
    }
    @AfterClass
    public static void clean() throws DAOException {
        dao.delete(valid);
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
}
