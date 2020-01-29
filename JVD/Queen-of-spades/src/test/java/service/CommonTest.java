package service;

import com.github.javafaker.Faker;
import com.loneliness.dao.DAOException;
import com.loneliness.dao.sql_dao_impl.UserDAO;
import com.loneliness.entity.*;
import com.loneliness.command.Command;
import com.loneliness.service.ServiceException;
import com.loneliness.command.common_comand.Create;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

public class CommonTest {
    private static Faker faker;
    private static Command<Integer,Integer, User> commonUserCommand;
    private static Command<Integer,Integer, Account> commonAccountCommand;
    private static Command<Integer,Integer, Message> commonMessageCommand;
    private static Command<Integer,Integer, News> commonNewsCommand;
    private static Command<Integer,Integer, Picture> commonPictureCommand;
    private static Command<Integer,Integer, PicturesInNews> commonPicturesInNewsCommand;
    private static Command<Integer,Integer, Profile> commonProfileCommand;
    @BeforeClass
    public static void init() throws DAOException {
        commonUserCommand=new Create<>(new <User>UserDAO());
    }


    private static User createValidUser(){
        User.Builder builder=new User.Builder();
        builder.setLogin(faker.name().username());
        builder.setPassword(faker.internet().password());
        User.Type type;
        if (faker.number().numberBetween(0, 1) == 0) {
            type = User.Type.ADMIN;
        } else {
            type = User.Type.USER;
        }
        builder.setType(type);
        builder.setLastUpdate(LocalDate.now());
        builder.setCreateDate(LocalDate.now());
        builder.setAvatarId(0);
        return builder.build();
    }
    @Test
    public void createTest() throws ServiceException {

        User user=createValidUser();
        commonUserCommand.execute(user);
        commonUserCommand.undo();
    }
}
